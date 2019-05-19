package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

/**
 * Accepts any enum. This is intended to be used as a default value in some places, and is not exposed in the config.
 *
 * @author Ameliorate
 */
public class AnyEnum<E extends Enum<E>> implements EnumMatcher<E> {
	public AnyEnum() {
	}

	public AnyEnum(Class<E> enumClass) {
		this.enumClass = enumClass;
	}

	public Class<E> enumClass = null;

	@Override
	public boolean matches(E matched) {
		return true;
	}

	@Override
	public E solve(E defaultValue) throws NotSolvableException {
		if (enumClass == null)
			throw new NotSolvableException("not able to solve an AnyEnum without a enumClass set");

		return enumClass.getEnumConstants()[0];
	}
}
