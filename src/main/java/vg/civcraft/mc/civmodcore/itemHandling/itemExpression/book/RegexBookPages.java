package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Ameliorate
 */
public class RegexBookPages implements BookPageMatcher {
	public RegexBookPages(Pattern regex) {
		this.regex = regex;
	}

	public Pattern regex;

	@Override
	public boolean matches(List<String> pages) {
		StringBuilder pageBuilder = new StringBuilder();
		for (String page : pages) {
			pageBuilder.append("\ueB0F");
			pageBuilder.append(page);
			pageBuilder.append('\ueE0F');
		}

		String formattedPages = pageBuilder.toString();
		return regex.matcher(formattedPages).find();
	}

	@Override
	public List<String> solve(List<String> defaultValue) throws NotSolvableException {
		throw new NotSolvableException("can't solve regexes");
	}
}
