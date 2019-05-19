package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
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

	@Override
	public MapView solve(MapView map) throws NotSolvableException {
		MapView view = Bukkit.createMap(Bukkit.getWorld("world"));
		view.addRenderer(new MapRenderer() {
			@Override
			public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
				// do nothing
			}
		});

		assert map.isVirtual();

		return view;
	}
}
