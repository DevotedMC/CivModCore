package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

public class AnyAmount implements AmountMatcher {
	@Override
	public boolean matches(int amount) {
		return true;
	}
}
