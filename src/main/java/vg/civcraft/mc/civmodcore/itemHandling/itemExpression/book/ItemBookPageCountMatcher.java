package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

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
}
