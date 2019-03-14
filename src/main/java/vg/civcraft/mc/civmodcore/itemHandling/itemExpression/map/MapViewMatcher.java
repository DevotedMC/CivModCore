package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.map.MapView;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

/**
 * @author Ameliorate
 */
public interface MapViewMatcher extends GenericMatcher<MapView> {
	boolean matches(MapView map);

	@Override
	default boolean genericMatches(MapView view) {
		return matches(view);
	}
}
