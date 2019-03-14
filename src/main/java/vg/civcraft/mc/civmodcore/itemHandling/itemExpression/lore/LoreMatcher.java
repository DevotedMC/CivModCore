package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

import java.util.List;

/**
 * @author Ameliorate
 */
public interface LoreMatcher extends GenericMatcher<List<String>> {
	boolean matches(List<String> lore);

	@Override
	default boolean genericMatches(List<String> lore) {
		return matches(lore);
	}
}
