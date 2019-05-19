package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher;

import java.util.List;

/**
 * @author Ameliorate
 */
public class EnumFromListMatcher<E extends Enum<E>> implements EnumMatcher<E> {
	public EnumFromListMatcher(List<E> enums, boolean notInList) {
		this.enums = enums;
		this.notInList = notInList;
	}

	public EnumFromListMatcher(List<E> enums) {
		this(enums, false);
	}

	public List<E> enums;

	/**
	 * If this should do "not in the list of enums" instead of the default of "is in the list of enums".
	 */
	public boolean notInList;

	@Override
	public boolean matches(E enumm) {
		if (notInList) {
			return !enums.contains(enumm);
		} else {
			return enums.contains(enumm);
		}
	}

	@Override
	public E solve(E defaultValue) throws NotSolvableException {
		return enums.get(0);
	}
}
