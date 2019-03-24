package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ItemMobSpawnerRequiredPlayerRangeMatcher implements ItemMatcher {
	public ItemMobSpawnerRequiredPlayerRangeMatcher(AmountMatcher range) {
		this.range = range;
	}

	public AmountMatcher range;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return range.matches(MobSpawnerUtil.getMobSpawnerState(item).getRequiredPlayerRange());
	}
}
