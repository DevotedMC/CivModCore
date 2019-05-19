package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.NameMatcher;

/**
 * @author Ameliorate
 */
public class ItemBookTitleMatcher implements ItemMatcher {
	public ItemBookTitleMatcher(NameMatcher title) {
		this.title = title;
	}

	public NameMatcher title;

	@Override
	public boolean matches(ItemStack item) {
		String title = "";

		if (item.hasItemMeta()) {
			if (item.getItemMeta() instanceof BookMeta && ((BookMeta) item.getItemMeta()).hasTitle()) {
				title = ((BookMeta) item.getItemMeta()).getTitle();
			} else {
				title = item.getItemMeta().getDisplayName(); // is this a good?
			}
		}

		return this.title.matches(title);
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BookMeta) ||
				!((BookMeta) item.getItemMeta()).hasTitle()) {
			item.setType(Material.WRITTEN_BOOK);
		}

		assert item.getItemMeta() instanceof BookMeta; // mostly to get intellij autocomplete for BookMeta

		String title = this.title.solve("Unnamed Book");
		BookMeta meta = (BookMeta) item.getItemMeta();
		meta.setTitle(title);
		item.setItemMeta(meta);
		return item;
	}
}
