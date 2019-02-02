package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Ameliorate
 */
public class RegexLore implements LoreMatcher {
	public RegexLore(List<Pattern> patterns) {
		this.patterns = patterns;
	}

	public RegexLore(Pattern... patterns) {
		this(Arrays.asList(patterns));
	}

	public List<Pattern> patterns;

	@Override
	public boolean matches(List<String> lore) {
		StringBuilder loreStr = new StringBuilder();
		for (String line : lore) {
			loreStr.append(line);
			loreStr.append('\n');
		}

		for (Pattern pattern : patterns) {
			if (pattern.matcher(loreStr).matches())
				return true;
		}
		return false;
	}
}
