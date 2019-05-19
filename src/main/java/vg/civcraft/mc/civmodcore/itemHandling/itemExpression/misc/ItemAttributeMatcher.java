package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.Matcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.NameMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid.UUIDMatcher;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Matches over the attributes of an item that apply for a given slot.
 *
 * @author Ameliorate
 */
public class ItemAttributeMatcher implements ItemMatcher {
	/**
	 * @param slot May be null, for an item that applies no matter what slot it is in.
	 */
	public ItemAttributeMatcher(List<AttributeMatcher> matchers, EquipmentSlot slot, ListMatchingMode mode) {
		this.matchers = matchers;
		this.slot = slot;
		this.mode = mode;

		matchers.forEach((m) -> m.slot = slot);
	}

	public List<AttributeMatcher> matchers;
	public EquipmentSlot slot;
	public ListMatchingMode mode;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !item.getItemMeta().hasAttributeModifiers())
			return false;

		return mode.matches(matchers, item.getItemMeta().getAttributeModifiers(slot).entries());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());

		List<Map.Entry<Attribute, AttributeModifier>> attributes = mode.solve(matchers,
				() -> new AbstractMap.SimpleEntry<>(Attribute.GENERIC_ARMOR,
						new AttributeModifier("a", 1, AttributeModifier.Operation.ADD_NUMBER)));

		Multimap<Attribute, AttributeModifier> attributesMap =  HashMultimap.create();

		for (Map.Entry<Attribute, AttributeModifier> entry : attributes) {
			attributesMap.put(entry.getKey(), entry.getValue());
		}

		meta.setAttributeModifiers(attributesMap);
		item.setItemMeta(meta);
		return item;
	}

	public static class AttributeMatcher implements Matcher<Map.Entry<Attribute, AttributeModifier>> {
		public AttributeMatcher(EnumMatcher<Attribute> attribute,
								NameMatcher name, EnumMatcher<AttributeModifier.Operation> operation,
								UUIDMatcher uuid, AmountMatcher amount) {
			this.attribute = attribute;
			this.name = name;
			this.operation = operation;
			this.uuid = uuid;
			this.amount = amount;
		}

		public EnumMatcher<Attribute> attribute;
		public EnumMatcher<AttributeModifier.Operation> operation;
		public NameMatcher name;
		public UUIDMatcher uuid;
		public AmountMatcher amount;

		private EquipmentSlot slot;

		public boolean matches(Attribute attribute, AttributeModifier modifier) {
			if (this.attribute != null && !this.attribute.matches(attribute))
			return false;
			else if (name != null && !name.matches(modifier.getName()))
				return false;
			else if (operation != null && !operation.matches(modifier.getOperation()))
				return false;
			else if (uuid != null && !uuid.matches(modifier.getUniqueId()))
				return false;
			else if (amount != null && !amount.matches(modifier.getAmount()))
				return false;
			else
				return true;
		}

		@Override
		public boolean matches(Map.Entry<Attribute, AttributeModifier> matched) {
			return matches(matched.getKey(), matched.getValue());
		}

		@Override
		public Map.Entry<Attribute, AttributeModifier> solve(Map.Entry<Attribute, AttributeModifier> entry) throws NotSolvableException {
			Attribute attribute = this.attribute.solve(entry.getKey());

			AttributeModifier defaultModifier = entry.getValue();

			AttributeModifier.Operation operation = this.operation.solve(defaultModifier.getOperation());
			String name = this.name.solve(defaultModifier.getName());
			UUID uuid = this.uuid.solve(defaultModifier.getUniqueId());
			double amount = this.amount.solve(defaultModifier.getAmount());

			return new AbstractMap.SimpleEntry<>(attribute, new AttributeModifier(uuid, name, amount, operation, slot));
		}
	}
}
