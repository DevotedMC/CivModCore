package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name;

/**
 * @author Ameliorate
 */
public class ExactlyName implements NameMatcher {
	public ExactlyName(String name) {
		this.name = name;
	}

	public ExactlyName(String name, boolean caseSensitive) {
		this.name = name;
		this.caseSensitive = caseSensitive;
	}

	public String name;
	public boolean caseSensitive = true;

	@Override
	public boolean matches(String name) {
		if (caseSensitive)
			return this.name.equals(name);
		else
			return this.name.equals(name.toLowerCase());
	}

	@Override
	public String solve(String defaultValue) throws NotSolvableException {
		return name;
	}
}
