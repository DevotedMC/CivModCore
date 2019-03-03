package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.NameMatcher;

/**
 * @author Ameliorate
 */
public class ItemBookAuthorMatcher implements ItemMatcher {
	public ItemBookAuthorMatcher(NameMatcher author) {
		this.author = author;
	}

	public NameMatcher author;

	@Override
	public boolean matches(ItemStack item) {
		String author = "";
		if (item.hasItemMeta() && item.getItemMeta() instanceof BookMeta && ((BookMeta) item.getItemMeta()).hasAuthor()) {
			author = ((BookMeta) item.getItemMeta()).getAuthor();
		}

		return this.author.matches(author);
	}
}
