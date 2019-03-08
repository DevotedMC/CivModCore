package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

/**
 * @author Ameliorate
 *
 * In addition to the amount of items in an ItemStack, these are also used for the durability of an item, and
 * the level of an enchantment.
 */
public interface AmountMatcher {
	default boolean matches(int amount) {
		return matches((double) amount);
	}

	boolean matches(double amount);
}
