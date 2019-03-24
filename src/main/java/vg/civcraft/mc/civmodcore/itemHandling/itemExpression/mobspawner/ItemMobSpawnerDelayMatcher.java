package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Amelorate
 */
public class ItemMobSpawnerDelayMatcher implements ItemMatcher {
	public ItemMobSpawnerDelayMatcher(AmountMatcher delay) {
		this.delay = delay;
	}

	public AmountMatcher delay;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return delay.matches(MobSpawnerUtil.getMobSpawnerState(item).getDelay());
	}
}
