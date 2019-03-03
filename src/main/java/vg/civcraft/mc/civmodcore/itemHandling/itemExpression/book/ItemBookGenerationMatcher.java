package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.List;

/**
 * @author Ameliorate
 */
public class ItemBookGenerationMatcher implements ItemMatcher {
	public ItemBookGenerationMatcher(List<BookMeta.Generation> generations) {
		this.generations = generations;
	}

	public List<BookMeta.Generation> generations;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BookMeta) || !((BookMeta) item.getItemMeta()).hasGeneration()) {
			return false;
		}

		return generations.contains(((BookMeta) item.getItemMeta()).getGeneration());
	}
}
