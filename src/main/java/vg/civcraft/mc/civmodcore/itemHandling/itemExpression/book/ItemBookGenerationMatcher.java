package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;

/**
 * @author Ameliorate
 */
public class ItemBookGenerationMatcher implements ItemMatcher {
	public ItemBookGenerationMatcher(EnumMatcher<BookMeta.Generation> generations) {
		this.generations = generations;
	}

	public EnumMatcher<BookMeta.Generation> generations;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BookMeta) || !((BookMeta) item.getItemMeta()).hasGeneration()) {
			return false;
		}

		return generations.matches(((BookMeta) item.getItemMeta()).getGeneration());
	}
}
