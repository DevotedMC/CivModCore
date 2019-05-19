package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.map.MapView;

/**
 * @author Ameliorate
 */
public class IsUnlimitedTrackingMapView implements MapViewMatcher {
	public IsUnlimitedTrackingMapView(boolean isUmlimitedTracking) {
		this.isUmlimitedTracking = isUmlimitedTracking;
	}

	public boolean isUmlimitedTracking;

	@Override
	public boolean matches(MapView map) {
        return map.isUnlimitedTracking() == isUmlimitedTracking;
	}

	@Override
	public MapView solve(MapView map) throws NotSolvableException {
		map.setUnlimitedTracking(isUmlimitedTracking);

		return map;
	}
}
