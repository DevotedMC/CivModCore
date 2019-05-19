package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * @author Ameliorate
 */
public class ItemMapViewMatcher implements ItemMatcher {
	public ItemMapViewMatcher(MapViewMatcher matcher) {
		this.matcher = matcher;
	}

	public MapViewMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof MapMeta) ||
				!((MapMeta) item.getItemMeta()).hasMapView())
			return false;

		return matcher.matches(((MapMeta) item.getItemMeta()).getMapView());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof MapMeta))
			item.setType(Material.MAP);

		MapView view = Bukkit.createMap(Bukkit.getWorld("world"));
		view = matcher.solve(view);

		MapMeta meta = (MapMeta) item.getItemMeta();
		meta.setMapView(view);
		item.setItemMeta(meta);

		return item;
	}
}
