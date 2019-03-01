package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.ItemMap;

public interface ItemMapMatcher extends ItemMatcher {
	boolean matches(ItemMap itemMap, ItemStack item);
}
