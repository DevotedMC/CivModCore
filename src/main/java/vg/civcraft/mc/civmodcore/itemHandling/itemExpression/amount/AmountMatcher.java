package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

/**
 * @author Ameliorate
 *
 * In addition to the amount of items in an ItemStack, these are also used for the durability of an item, and
 * the level of an enchantment.
 */
public interface AmountMatcher extends GenericMatcher<Double> {
	default boolean matches(int amount) {
		return matches((double) amount);
	}

	boolean matches(double amount);

	@Override
	default boolean genericMatches(Double amount) {
		return matches(amount);
	}
}
