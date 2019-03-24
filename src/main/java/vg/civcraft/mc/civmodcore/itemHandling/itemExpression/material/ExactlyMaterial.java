package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material;

import org.bukkit.Material;

/**
 * @author Ameliorate
 */
public class ExactlyMaterial implements MaterialMatcher {
	public ExactlyMaterial(Material material) {
		this.material = material;
	}

	public Material material;

	@Override
	public boolean matches(Material material) {
		return this.material == material;
	}
}
