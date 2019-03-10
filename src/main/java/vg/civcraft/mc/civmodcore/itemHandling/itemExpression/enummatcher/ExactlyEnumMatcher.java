package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

/**
 * @author Ameliorate
 */
public class ExactlyEnumMatcher<E extends Enum<E>> implements EnumMatcher<E> {
	public ExactlyEnumMatcher(E exactly) {
		this.exactly = exactly;
	}

	public E exactly;

	@Override
	public boolean matches(E enumm) {
		return exactly.equals(enumm);
	}
}
