package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import java.util.List;

/**
 * @author Ameliorate
 */
public class ExactlyBookPages implements BookPageMatcher {
	public ExactlyBookPages(List<String> pages) {
		this.pages = pages;
	}

	public List<String> pages;

	@Override
	public boolean matches(List<String> pages) {
		return this.pages.equals(pages);
	}
}
