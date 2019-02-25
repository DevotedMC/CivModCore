package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * @author Ameliorate
 */
public class ItemAmountMatcher implements ItemMatcher {
	public ItemAmountMatcher(AmountMatcher matcher) {
		this.matcher = matcher;
	}

	public AmountMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		return matcher.matches(item.getAmount());
	}
}
