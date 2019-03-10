package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

/**
 * @author Ameliorate
 */
public class EnumIndexMatcher<E extends Enum<E>> implements EnumMatcher<E> {
	public EnumIndexMatcher(int index) {
		this.index = index;
	}

	int index;

	@Override
	public boolean matches(E enumm) {
		return enumm.ordinal() == index;
	}
}
