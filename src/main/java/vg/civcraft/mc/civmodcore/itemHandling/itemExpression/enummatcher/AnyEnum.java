package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

/**
 * Accepts any enum. This is intended to be used as a default value in some places, and is not exposed in the config.
 *
 * @author Ameliorate
 */
public class AnyEnum<E extends Enum<E>> implements EnumMatcher<E> {
	@Override
	public boolean matches(E matched) {
		return true;
	}
}
