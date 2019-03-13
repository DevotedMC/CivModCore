package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ItemMobSpawnerSpawnCountMatcher implements ItemMatcher {
	public ItemMobSpawnerSpawnCountMatcher(AmountMatcher spawnCount) {
		this.spawnCount = spawnCount;
	}

	public AmountMatcher spawnCount;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return spawnCount.matches(MobSpawnerUtil.getMobSpawnerState(item).getSpawnCount());
	}
}
