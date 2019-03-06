package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

/**
 * Represents the different ways of interpreting a list of things when comparing it to another list.
 *
 * @author Ameliorate
 */
public enum ListMatchingMode {
	/**
	 * At least one element in the list must match an element in the other list.
	 */
	ANY,

	/**
	 * Every one of the elements in the list must match an element in the other list.
	 */
	ALL,

	/**
	 * No element in the list may match an element in the other list.
	 */
	NONE,
}
