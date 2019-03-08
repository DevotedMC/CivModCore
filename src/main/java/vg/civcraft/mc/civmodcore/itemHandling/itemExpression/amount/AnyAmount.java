package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

/**
 * Accepts any amount.
 *
 * @author Ameliorate
 */
public class AnyAmount implements AmountMatcher {
	@Override
	public boolean matches(double amount) {
		return true;
	}
}
