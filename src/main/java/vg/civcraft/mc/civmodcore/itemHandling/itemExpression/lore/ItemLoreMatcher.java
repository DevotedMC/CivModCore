package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemLoreMatcher implements ItemMatcher {
	public ItemLoreMatcher(LoreMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemLoreMatcher construct(Optional<LoreMatcher> matcher) {
		return matcher.map(ItemLoreMatcher::new).orElse(null);
	}

	public LoreMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
            return false;

		return matcher.matches(item.getItemMeta().getLore());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());

		meta.setLore(matcher.solve(meta.getLore()));
		item.setItemMeta(meta);
		return item;
	}
}
