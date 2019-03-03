package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * Matches an item if the item's meta is not of a specific type.
 *
 * For example, if itemMetaInterface=EnchantmentStorageMeta, this will match any item whose ItemMeta does not implement
 * EnchantmentStorageMeta.
 *
 * @author Ameliorate
 */
public class ItemMetaNotInstanceOfMatcher implements ItemMatcher {
	public ItemMetaNotInstanceOfMatcher(Class<? extends ItemMeta> itemMetaInterface) {
		this.itemMetaInterface = itemMetaInterface;
	}

	public Class<? extends ItemMeta> itemMetaInterface;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta())
			// an item with no meta has a meta that does not implement itemMetaInterface.
			return true;
		return !itemMetaInterface.isAssignableFrom(item.getItemMeta().getClass());
	}
}
