package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.color.ColorMatcher;

/**
 * @author Ameliorate
 */
public class ItemMapColorMatcher implements ItemMatcher {
	public ItemMapColorMatcher(ColorMatcher color) {
		this.color = color;
	}

	public ColorMatcher color;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof MapMeta) ||
				!((MapMeta) item.getItemMeta()).hasColor())
			return false;

		return color.matches(((MapMeta) item.getItemMeta()).getColor());
	}
}
