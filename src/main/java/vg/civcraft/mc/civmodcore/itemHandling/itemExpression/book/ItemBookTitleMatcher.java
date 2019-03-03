package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

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
}
