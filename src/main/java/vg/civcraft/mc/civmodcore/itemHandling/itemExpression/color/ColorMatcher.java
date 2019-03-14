package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.color;

import org.bukkit.Color;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

/**
 * @author Ameliorate
 */
public interface ColorMatcher extends GenericMatcher<Color> {
	boolean matches(Color color);

	@Override
	default boolean genericMatches(Color color) {
		return matches(color);
	}
}
