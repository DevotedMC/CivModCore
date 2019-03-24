package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.map.MapView;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;

/**
 * @author Ameliorate
 */
public class ScaleMapView implements MapViewMatcher {
	public ScaleMapView(EnumMatcher<MapView.Scale> scale) {
		this.scale = scale;
	}

	public EnumMatcher<MapView.Scale> scale;

	@Override
	public boolean matches(MapView map) {
		return scale.matches(map.getScale());
	}
}
