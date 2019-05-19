package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book;

import org.bukkit.Material;
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

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BookMeta) ||
				!((BookMeta) item.getItemMeta()).hasAuthor()) {
			item.setType(Material.WRITTEN_BOOK);
		}

		assert item.getItemMeta() instanceof BookMeta; // mostly to get intellij autocomplete for BookMeta

		BookMeta meta = (BookMeta) item.getItemMeta();
		meta.setAuthor(author.solve("Nobody"));
		item.setItemMeta(meta);
		return item;
	}
}
