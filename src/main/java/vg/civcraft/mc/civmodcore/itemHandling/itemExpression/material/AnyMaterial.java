package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material;

import org.bukkit.Material;

public class AnyMaterial implements MaterialMatcher {
	@Override
	public boolean matches(Material material) {
		return true;
	}
}
