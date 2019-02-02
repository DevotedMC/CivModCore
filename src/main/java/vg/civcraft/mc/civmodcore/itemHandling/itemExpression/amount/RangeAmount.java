package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

/**
 * @author Ameliorate
 */
public class RangeAmount implements AmountMatcher {
	public RangeAmount(int low, int high, boolean lowInclusive, boolean highInclusive) {
		set(low, high);
		this.highInclusive = highInclusive;
		this.lowInclusive = lowInclusive;
	}

	private int low;
	private int high;
	public boolean highInclusive;
	public boolean lowInclusive;

	public void set(int low, int high) {
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

	public int getLow() {
		return low;
	}

	public int getHigh() {
		return high;
	}

	@Override
	public boolean matches(int amount) {
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
