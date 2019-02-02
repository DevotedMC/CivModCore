package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

/**
 * @author Ameliorate
 */
public class AnyAmount implements AmountMatcher {
	@Override
	public boolean matches(int amount) {
		return true;
	}
}
