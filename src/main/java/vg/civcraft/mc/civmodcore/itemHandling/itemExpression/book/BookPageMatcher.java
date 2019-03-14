package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

import java.util.List;

/**
 * @author Ameliorate
 */
public interface BookPageMatcher extends GenericMatcher<List<String>> {
	/**
	 * @param pages A list of multi-line strings that represent the pages of a written book. Colours left intact in the
	 *              section character format.
	 * @return If the pages matched.
	 */
	boolean matches(List<String> pages);

	@Override
	default boolean genericMatches(List<String> matched) {
		return matches(matched);
	}
}
