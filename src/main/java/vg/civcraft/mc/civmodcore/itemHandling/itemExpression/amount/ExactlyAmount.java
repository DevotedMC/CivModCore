package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

/**
 * Accepts an amount exactly equal to the amount passed in.
 *
 * @author Ameliorate
 */
public class ExactlyAmount implements AmountMatcher {
	public ExactlyAmount(double amount) {
		this.amount = amount;
	}

	public double amount;

	@Override
	public boolean matches(double amount) {
		return this.amount == amount;
	}
}
