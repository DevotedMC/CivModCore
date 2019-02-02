package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

public class ExactlyAmount implements AmountMatcher {
	public ExactlyAmount(int amount) {
		this.amount = amount;
	}

	public int amount;

	@Override
	public boolean matches(int amount) {
		return this.amount == amount;
	}
}
