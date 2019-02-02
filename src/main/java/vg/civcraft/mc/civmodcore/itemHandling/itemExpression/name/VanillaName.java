package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name;

/**
 * Makes sure the name is what shows up when you have not renamed the item.
 *
 * Actually just checks for null.
 *
 * @author Ameliorate
 */
public class VanillaName implements NameMatcher {
	@Override
	public boolean matches(String name) {
		return name == null;
	}
}
