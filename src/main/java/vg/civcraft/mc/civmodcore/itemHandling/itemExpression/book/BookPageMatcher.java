package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import java.util.List;

/**
 * @author Ameliorate
 */
public interface BookPageMatcher {
	/**
	 * @param pages A list of multi-line strings that represent the pages of a written book. Colours left intact in the
	 *              section character format.
	 * @return If the pages matched.
	 */
	boolean matches(List<String> pages);
}
