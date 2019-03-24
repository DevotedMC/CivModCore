package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.World;
import org.bukkit.map.MapView;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.NameMatcher;

/**
 * @author Ameliorate
 */
public class WorldMapView implements MapViewMatcher {
	public WorldMapView(NameMatcher world) {
		this.world = world;
	}

	public NameMatcher world;

	@Override
	public boolean matches(MapView map) {
		World world = map.getWorld();

		return this.world.matches(world.getName());
	}
}
