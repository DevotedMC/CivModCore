package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
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
}
