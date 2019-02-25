package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * @author Ameliorate
 */
public class ItemLoreMatcher implements ItemMatcher {
	public ItemLoreMatcher(LoreMatcher matcher) {
		this.matcher = matcher;
	}

	public LoreMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		return item.hasItemMeta() && matcher.matches(item.getItemMeta().getLore());
	}
}
