package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.ItemMap;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMapMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemAmountMatcher implements ItemMatcher, ItemMapMatcher {
	public ItemAmountMatcher(AmountMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemAmountMatcher construct(Optional<AmountMatcher> matcher) {
		return matcher.map(ItemAmountMatcher::new).orElse(null);
	}

	public AmountMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		return matcher.matches(item.getAmount());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		item.setAmount(matcher.solve(1));
		return item;
	}

	@Override
	public boolean matches(ItemMap itemMap, ItemStack item) {
		return matcher.matches(itemMap.getAmount(item));
	}
}
