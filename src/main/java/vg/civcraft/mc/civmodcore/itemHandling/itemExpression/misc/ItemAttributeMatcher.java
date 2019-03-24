package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.Matcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.NameMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid.UUIDMatcher;

import java.util.List;
import java.util.Map;

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
	}
}
