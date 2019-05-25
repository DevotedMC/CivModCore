package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.NameMatcher;

import java.util.Arrays;

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
		return nameMatcher.matches(enumm.name());
	}

	@Override
	public E solve(E defaultValue) throws NotSolvableException {
		String name = nameMatcher.solve(defaultValue.name());
		return Arrays.stream(enumClass.getEnumConstants())
				.filter((e) -> name.equals(e.name()))
				.findFirst()
				.orElseThrow(() -> new NotSolvableException(
						"name of enum " + name + " does not match any variants of enum "+ enumClass.getName()));
	}
}
