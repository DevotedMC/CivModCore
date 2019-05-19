package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

import java.util.Collections;
import java.util.List;

/**
 * @author Ameliorate
 */
public class ItemBookPageCountMatcher implements ItemMatcher {
	public ItemBookPageCountMatcher(AmountMatcher pageCount) {
		this.pageCount = pageCount;
	}

	public AmountMatcher pageCount;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BookMeta))
			return false;

		return pageCount.matches(((BookMeta) item.getItemMeta()).getPageCount());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BookMeta)) {
			item.setType(Material.WRITABLE_BOOK);
		}

		assert item.getItemMeta() instanceof BookMeta;

		int count = pageCount.solve(1);
		List<String> pages = Collections.nCopies(count, "");

		BookMeta meta = (BookMeta) item.getItemMeta();
		meta.setPages(pages);
		item.setItemMeta(meta);
		return item;
	}
}
