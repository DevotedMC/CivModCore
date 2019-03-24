package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.Matcher;

/**
 * Matches over an Enum in an enum-generic way.
 *
 * @param <E> The enum being matched over.
 *
 * @author Ameliorate
 */
public interface EnumMatcher<E extends Enum<E>> extends Matcher<E> {
}
