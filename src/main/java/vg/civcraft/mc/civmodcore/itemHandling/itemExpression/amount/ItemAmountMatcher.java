package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.ItemMap;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMapMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * @author Ameliorate
 */
public class ItemAmountMatcher implements ItemMatcher, ItemMapMatcher {
	public ItemAmountMatcher(AmountMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemAmountMatcher construct(AmountMatcher matcher) {
		if (matcher == null)
			return null;

		return new ItemAmountMatcher(matcher);
	}

	public AmountMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		return matcher.matches(item.getAmount());
	}

	@Override
	public boolean matches(ItemMap itemMap, ItemStack item) {
		return matcher.matches(itemMap.getAmount(item));
	}
}
