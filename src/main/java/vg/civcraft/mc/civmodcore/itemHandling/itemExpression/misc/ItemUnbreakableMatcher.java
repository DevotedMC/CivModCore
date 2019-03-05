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

	public boolean unbreakable;

	@Override
	public boolean matches(ItemStack item) {
		boolean isUnbreakable = false;
		if (item.hasItemMeta())
			// an item without metadata can not be unbreakable
			isUnbreakable = item.getItemMeta().isUnbreakable();
		return isUnbreakable == unbreakable;
	}
}
