package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

/**
 * Matches over an Enum in an enum-generic way.
 *
 * @param <E> The enum being matched over.
 *
 * @author Ameliorate
 */
public interface EnumMatcher<E extends Enum<E>> extends GenericMatcher<E> {
	boolean matches(E enumm);

	@Override
	default boolean genericMatches(E matched) {
		return matches(matched);
	}
}
