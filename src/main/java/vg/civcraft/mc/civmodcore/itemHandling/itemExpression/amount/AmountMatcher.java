package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.Matcher;

/**
 * @author Ameliorate
 *
 * In addition to the amount of items in an ItemStack, these are also used for the durability of an item, and
 * the level of an enchantment.
 */
public interface AmountMatcher extends Matcher<Double> {
	default boolean matches(int amount) {
		return matches((double) amount);
	}

	default int solve(int defaultAmount) throws NotSolvableException {
		return (int) (double) solve((double) defaultAmount);
	}
}
