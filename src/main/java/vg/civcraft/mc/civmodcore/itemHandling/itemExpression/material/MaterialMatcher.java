package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material;

import org.bukkit.Material;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

/**
 * @author Ameliorate
 */
public interface MaterialMatcher extends GenericMatcher<Material> {
	boolean matches(Material material);

	@Override
	default boolean genericMatches(Material matched) {
		return matches(matched);
	}
}
