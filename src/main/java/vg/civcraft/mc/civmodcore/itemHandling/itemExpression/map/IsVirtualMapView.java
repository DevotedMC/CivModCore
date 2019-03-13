package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.map.MapView;

/**
 * @author Ameliorate
 */
public class IsVirtualMapView implements MapViewMatcher {
	public IsVirtualMapView(boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	public boolean isVirtual;

	@Override
	public boolean matches(MapView map) {
		return map.isVirtual() == isVirtual;
	}
}
