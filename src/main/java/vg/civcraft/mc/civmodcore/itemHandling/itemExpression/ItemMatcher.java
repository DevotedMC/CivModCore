package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

/**
 * Represents a single property of an item that should be checked.
 *
 * If any one of these reject an item by returning false, ItemExpression.matches(ItemStack) will return false.
 *
 * @author Ameliorate
 */
public interface ItemMatcher extends GenericMatcher<ItemStack> {
	boolean matches(ItemStack item);

	@Override
	default boolean genericMatches(ItemStack matched) {
		return matches(matched);
	}
}
