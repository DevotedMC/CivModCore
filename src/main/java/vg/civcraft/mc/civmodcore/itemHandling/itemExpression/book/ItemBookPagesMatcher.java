package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ameliorate
 */
public class ItemBookPagesMatcher implements ItemMatcher {
	public ItemBookPagesMatcher(BookPageMatcher matcher) {
		this.matcher = matcher;
	}

	public BookPageMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		List<String> pages = Collections.emptyList();

		if (item.hasItemMeta() && item.getItemMeta() instanceof BookMeta && ((BookMeta) item.getItemMeta()).hasPages()) {
			pages = ((BookMeta) item.getItemMeta()).getPages();
		}

		return matcher.matches(pages);
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BookMeta)) {
			item.setType(Material.WRITABLE_BOOK);
		}

		assert item.getItemMeta() instanceof BookMeta;

		List<String> pages = matcher.solve(new ArrayList<>());
		// new arraylist instead of Collections.emptyList() because solve() might mutate the list.

		BookMeta meta = (BookMeta) item.getItemMeta();
		meta.setPages(pages);
		item.setItemMeta(meta);
		return item;
	}
}
