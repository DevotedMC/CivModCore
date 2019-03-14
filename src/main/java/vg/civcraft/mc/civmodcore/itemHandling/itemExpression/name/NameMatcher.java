package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

/**
 * @author Ameliorate
 */
public interface NameMatcher extends GenericMatcher<String> {
	boolean matches(String name);

	@Override
	default boolean genericMatches(String matched) {
		return matches(matched);
	}
}
