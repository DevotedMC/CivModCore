package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * @author Ameliorate
 */
public class ItemBookNoGenerationMatcher implements ItemMatcher {
	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BookMeta))
			return false;
		return ((BookMeta) item.getItemMeta()).hasGeneration();
	}
}
