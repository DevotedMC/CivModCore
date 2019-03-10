package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.NameMatcher;

/**
 * @author Ameliorate
 */
public class NameEnumMatcher<E extends Enum<E>> implements EnumMatcher<E> {
	public NameEnumMatcher(NameMatcher nameMatcher) {
		this.nameMatcher = nameMatcher;
	}

	public NameMatcher nameMatcher;

	@Override
	public boolean matches(E enumm) {
		return nameMatcher.matches(enumm.toString());
	}
}
