package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.inventory;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * Matches an item that has an empty inventory, or no inventory at all.
 *
 * @author Ameliorate
 */
public class ItemEmptyInventoryMatcher implements ItemMatcher {
	@Override
	public boolean matches(ItemStack item) {
		return ItemExactlyInventoryMatcher.getItemHeldInventory(item).getTotalItemAmount() == 0;
	}
}
