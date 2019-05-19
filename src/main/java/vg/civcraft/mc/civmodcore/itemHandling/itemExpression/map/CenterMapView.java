package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.map.MapView;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class CenterMapView implements MapViewMatcher {
	public CenterMapView(AmountMatcher mapLocation, CenterCoordinate centerCoordinate) {
		this.mapLocation = mapLocation;
		this.centerCoordinate = centerCoordinate;
	}

	public AmountMatcher mapLocation;
	public CenterCoordinate centerCoordinate;

	@Override
	public boolean matches(MapView map) {
		int coordinate = centerCoordinate == CenterCoordinate.X ? map.getCenterX() : map.getCenterZ();

		return mapLocation.matches(coordinate);
	}

	@Override
	public MapView solve(MapView view) throws NotSolvableException {
		switch (centerCoordinate) {
			case X:
				view.setCenterX(mapLocation.solve(0));
				break;
			case Z:
				view.setCenterZ(mapLocation.solve(0));
				break;
		}

		return view;
	}

	public enum CenterCoordinate {
		X,
		Z,
	}
}
