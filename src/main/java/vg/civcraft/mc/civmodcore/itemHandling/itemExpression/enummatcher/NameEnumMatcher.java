package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.NameMatcher;

/**
 * @author Ameliorate
 */
public class NameEnumMatcher<E extends Enum<E>> implements EnumMatcher<E> {
	public NameEnumMatcher(NameMatcher nameMatcher) {
		this.nameMatcher = nameMatcher;
	}

	public NameEnumMatcher(NameMatcher nameMatcher, Class<E> enumClass) {
		this(nameMatcher);
		this.enumClass = enumClass;
	}

	public NameMatcher nameMatcher;
	public Class<E> enumClass = null;

	@Override
	public boolean matches(E enumm) {
		return nameMatcher.matches(enumm.toString());
	}

	@Override
	public E solve(E defaultValue) throws NotSolvableException {
		return null;
	}
}
