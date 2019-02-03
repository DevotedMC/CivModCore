package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

/**
 * @author Ameliorate
 *
 * Also used to match the level on an enchantment.
 */
public interface AmountMatcher {
	boolean matches(int amount);
}
