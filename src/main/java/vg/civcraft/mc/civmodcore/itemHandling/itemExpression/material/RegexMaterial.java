package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material;

import org.bukkit.Material;

import java.util.regex.Pattern;

/**
 * @author Ameliorate
 */
public class RegexMaterial implements MaterialMatcher {
	public RegexMaterial(Pattern regex) {
		this.regex = regex;
	}

	public Pattern regex;

	@Override
	public boolean matches(Material material) {
		return regex.matcher(material.toString()).matches();
	}
}
