package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemNameMatcher implements ItemMatcher {
	public ItemNameMatcher(NameMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemNameMatcher construct(Optional<NameMatcher> matcher) {
		return matcher.map(ItemNameMatcher::new).orElse(null);
	}

	public NameMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		String name;
		if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName())
			name = "";
		else
			name = item.getItemMeta().getDisplayName();
		return matcher.matches(name);
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		ItemMeta meta;
		if (!item.hasItemMeta())
			meta = Bukkit.getItemFactory().getItemMeta(item.getType());
		else
			meta = item.getItemMeta();

		meta.setDisplayName(matcher.solve(meta.getDisplayName()));
		item.setItemMeta(meta);
		return item;
	}
}
