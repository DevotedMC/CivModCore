package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.firework;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ItemFireworkPowerMatcher implements ItemMatcher {
	public ItemFireworkPowerMatcher(AmountMatcher power) {
		this.power = power;
	}

	public AmountMatcher power;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof FireworkMeta))
			return false;

		return power.matches(((FireworkMeta) item.getItemMeta()).getPower());
	}
}
