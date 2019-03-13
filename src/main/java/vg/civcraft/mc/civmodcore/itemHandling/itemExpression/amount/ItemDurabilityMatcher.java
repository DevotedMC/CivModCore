package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * @author Ameliorate
 */
public class ItemDurabilityMatcher implements ItemMatcher {
	public ItemDurabilityMatcher(AmountMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemDurabilityMatcher construct(AmountMatcher matcher) {
		if (matcher == null)
			return null;

		return new ItemDurabilityMatcher(matcher);
	}

	public AmountMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof Damageable))
            return false;

		return matcher.matches(((Damageable) item.getItemMeta()).getDamage());
	}
}
