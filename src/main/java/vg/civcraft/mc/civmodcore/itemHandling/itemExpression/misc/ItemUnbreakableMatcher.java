package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * @author Ameliorate
 */
public class ItemUnbreakableMatcher implements ItemMatcher {
	public ItemUnbreakableMatcher(boolean unbreakable) {
		this.unbreakable = unbreakable;
	}

	boolean unbreakable;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta())
			return false;
		return item.getItemMeta().isUnbreakable() == unbreakable;
	}
}
