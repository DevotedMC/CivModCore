package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.NameMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid.UUIDMatcher;

import java.util.List;
import java.util.function.Predicate;

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

		return item.getItemMeta().getAttributeModifiers(slot).entries().stream().anyMatch((e) -> {
			Attribute attribute = e.getKey();
			AttributeModifier modifier = e.getValue();

			Predicate<AttributeMatcher> matcherMatchesPredicate = (matcher) -> matcher.matches(attribute, modifier);

			switch (mode) {
				case ANY:
					return matchers.stream().anyMatch(matcherMatchesPredicate);
				case ALL:
					return matchers.stream().allMatch(matcherMatchesPredicate);
				case NONE:
					return matchers.stream().noneMatch(matcherMatchesPredicate);
			}

			throw new AssertionError("not reachable");
		});
	}

	public static class AttributeMatcher {
		public AttributeMatcher(NameMatcher attribute,
								NameMatcher name, NameMatcher operation,
								UUIDMatcher uuid, AmountMatcher amount) {
			this.attribute = attribute;
			this.name = name;
			this.operation = operation;
			this.uuid = uuid;
			this.amount = amount;
		}

		public NameMatcher attribute;
		public NameMatcher name;
		public NameMatcher operation;
		public UUIDMatcher uuid;
		public AmountMatcher amount;

		public boolean matches(Attribute attribute, AttributeModifier modifier) {
			if (this.attribute != null && !this.attribute.matches(attribute.toString()))
			return false;
			else if (name != null && !name.matches(modifier.getName()))
				return false;
			else if (operation != null && !operation.matches(modifier.getOperation().toString()))
				return false;
			else if (uuid != null && !uuid.matches(modifier.getUniqueId()))
				return false;
			else if (amount != null && !amount.matches(modifier.getAmount()))
				return false;
			else
				return true;
		}
	}
}
