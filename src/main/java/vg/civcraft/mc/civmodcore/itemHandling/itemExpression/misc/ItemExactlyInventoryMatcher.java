package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import vg.civcraft.mc.civmodcore.itemHandling.ItemMap;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemExpression;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.List;

/**
 * Matches an item if all of the ItemExpressions match the items within the inventory in a 1:1 fashion.
 *
 * See also ItemMap.itemExpressionsMatchItems().
 *
 * @author Ameliorate
 */
public class ItemExactlyInventoryMatcher implements ItemMatcher {
	public ItemExactlyInventoryMatcher(List<ItemExpression> itemExpressions) {
		this.itemExpressions = itemExpressions;
	}

	public List<ItemExpression> itemExpressions;

	@Override
	public boolean matches(ItemStack item) {
		return getItemHeldInventory(item).itemExpressionsMatchItems(itemExpressions);
	}

	/**
	 * @param item The item to get the inventory of.
	 * @return An ItemMap of the item's inventory.
	 * If the item does not have an inventory or has an empty inventory, returns an empty ItemMap.
	 */
	public static ItemMap getItemHeldInventory(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BlockStateMeta) ||
				!((BlockStateMeta) item.getItemMeta()).hasBlockState() ||
				!(((BlockStateMeta) item.getItemMeta()).getBlockState() instanceof Container))
			return new ItemMap();
		else
			return new ItemMap(((Container) ((BlockStateMeta) item.getItemMeta()).getBlockState()).getInventory());
	}
}
