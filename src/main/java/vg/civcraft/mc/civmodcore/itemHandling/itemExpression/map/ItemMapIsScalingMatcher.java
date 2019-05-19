package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * @author Ameliorate
 */
public class ItemMapIsScalingMatcher implements ItemMatcher {
	public ItemMapIsScalingMatcher(boolean isScaling) {
		this.isScaling = isScaling;
	}

	public boolean isScaling;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || (item.getItemMeta() instanceof MapMeta))
			return false;

		return ((MapMeta) item.getItemMeta()).isScaling() == isScaling;
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof MapMeta))
			item.setType(Material.MAP);

		MapMeta meta = (MapMeta) item.getItemMeta();
		meta.setScaling(isScaling);

		item.setItemMeta(meta);

		return item;
	}
}
