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
		return pattern.matcher(String.join("\n", lore)).find();
	}
}
