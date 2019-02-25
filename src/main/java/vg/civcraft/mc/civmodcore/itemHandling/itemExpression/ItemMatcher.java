package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

import org.bukkit.inventory.ItemStack;

/**
 * Represents a single property of an item that should be checked.
 *
 * If any one of these reject an item by returning false, ItemExpression.matches(ItemStack) will return false.
 *
 * @author Ameliorate
 */
public interface ItemMatcher {
	boolean matches(ItemStack item);
}
