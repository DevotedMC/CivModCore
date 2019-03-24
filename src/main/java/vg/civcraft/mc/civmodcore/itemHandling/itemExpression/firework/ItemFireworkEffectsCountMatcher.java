package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.firework;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ItemFireworkEffectsCountMatcher implements ItemMatcher {
	public ItemFireworkEffectsCountMatcher(AmountMatcher count) {
		this.count = count;
	}

	public AmountMatcher count;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof FireworkMeta))
			return false;

		return count.matches(((FireworkMeta) item.getItemMeta()).getEffectsSize());
	}
}
