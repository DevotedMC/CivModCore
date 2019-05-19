package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

/**
 * @author Ameliorate
 */
public class EnumIndexMatcher<E extends Enum<E>> implements EnumMatcher<E> {
	public EnumIndexMatcher(int index) {
		this.index = index;
	}

	public EnumIndexMatcher(int index, Class<E> enumClass) {
		this(index);
		this.enumClass = enumClass;
	}

	public int index;
	public Class<E> enumClass = null;

	@Override
	public boolean matches(E enumm) {
		return enumm.ordinal() == index;
	}

	@Override
	public E solve(E defaultValue) throws NotSolvableException {
		if (enumClass == null)
			throw new NotSolvableException("can't solve an EnumIndex without enumClass set");

		return enumClass.getEnumConstants()[index];
	}
}
