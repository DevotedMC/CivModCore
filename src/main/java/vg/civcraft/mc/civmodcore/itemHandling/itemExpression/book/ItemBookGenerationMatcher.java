package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.Material;
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

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BookMeta) ||
				!((BookMeta) item.getItemMeta()).hasGeneration()) {
			item.setType(Material.WRITTEN_BOOK);
		}

		assert item.getItemMeta() instanceof BookMeta; // mostly to get intellij autocomplete for BookMeta

		BookMeta meta = (BookMeta) item.getItemMeta();
		meta.setGeneration(generations.solve(BookMeta.Generation.ORIGINAL));
		item.setItemMeta(meta);
		return item;
	}
}
