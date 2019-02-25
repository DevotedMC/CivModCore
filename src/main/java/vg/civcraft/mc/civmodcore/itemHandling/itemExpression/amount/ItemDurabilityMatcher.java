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

	public AmountMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		int durability;
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof Damageable))
			durability = -1;
		else
			durability = ((Damageable) item.getItemMeta()).getDamage();
		return matcher.matches(durability);
	}
}
