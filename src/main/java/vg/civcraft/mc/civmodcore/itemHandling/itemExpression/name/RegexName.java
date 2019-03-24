package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name;

import java.util.regex.Pattern;

/**
 * @author Ameliorate
 */
public class RegexName implements NameMatcher {
	public RegexName(Pattern regex) {
		this.regex = regex;
	}

	public Pattern regex;

	@Override
	public boolean matches(String name) {
		return regex.matcher(name).matches();
	}
}
