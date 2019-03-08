package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

/**
 * Accepts an amount between high and low. Also allows selecting if the range should include high and low.
 *
 * @author Ameliorate
 */
public class RangeAmount implements AmountMatcher {
	/**
	 * @param low The lowest number that this range should accept.
	 * @param high The highest number that this range should accpet.
	 * @param lowInclusive If this range should accept values equal to low.
	 * @param highInclusive If this range should accept values equal to high.
	 */
	public RangeAmount(double low, double high, boolean lowInclusive, boolean highInclusive) {
		set(low, high);
		this.highInclusive = highInclusive;
		this.lowInclusive = lowInclusive;
	}

	private double low;
	private double high;
	public boolean highInclusive;
	public boolean lowInclusive;

	public void set(double low, double high) {
		if (low <= high) {
			// expected situation, do as normal
			this.low = low;
			this.high = high;
		} else {
			// accidentally reversed, fix it silently
			this.low = high;
			this.high = low;
		}
	}

	public double getLow() {
		return low;
	}

	public double getHigh() {
		return high;
	}

	@Override
	public boolean matches(double amount) {
		if (lowInclusive) {
			if (amount < low)
				return false;
		} else {
			if (amount <= low)
				return false;
		}
		if (highInclusive) {
			if (amount > high)
				return false;
		} else {
			if (amount >= high)
				return false;
		}

		return true;
	}
}
