package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name;

/**
 * @author Ameliorate
 */
public class ExactlyName implements NameMatcher {
	public ExactlyName(String name) {
		this.name = name;
	}

	public String name;

	@Override
	public boolean matches(String name) {
		return this.name.equals(name);
	}
}
