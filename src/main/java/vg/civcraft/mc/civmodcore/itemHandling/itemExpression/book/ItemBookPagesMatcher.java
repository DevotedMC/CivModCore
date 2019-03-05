package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

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
}
