package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Ameliorate
 */
public class RegexLore implements LoreMatcher {
	public RegexLore(Pattern pattern) {
		this.pattern = pattern;
	}

	public Pattern pattern;

	@Override
	public boolean matches(List<String> lore) {
		StringBuilder loreStr = new StringBuilder();
		for (String line : lore) {
			loreStr.append(line);
			loreStr.append('\n');
		}

		if (pattern.matcher(loreStr).matches())
			return true;
		return false;
	}
}
