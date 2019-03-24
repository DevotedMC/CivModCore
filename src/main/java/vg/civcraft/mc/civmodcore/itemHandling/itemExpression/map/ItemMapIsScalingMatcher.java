package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

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
}
