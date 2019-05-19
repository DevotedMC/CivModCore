package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.Bukkit;
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

	@Override
	public MapView solve(MapView view) throws NotSolvableException {
		String worldStr = world.solve("world");
		World world = Bukkit.getWorld(worldStr);

		if (world == null) {
			throw new NotSolvableException("world matcher does not solve to a valid world name");
		}

		view.setWorld(world);
		return view;
	}
}
