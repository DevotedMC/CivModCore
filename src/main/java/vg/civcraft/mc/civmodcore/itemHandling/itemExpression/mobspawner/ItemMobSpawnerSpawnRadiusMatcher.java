package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ItemMobSpawnerSpawnRadiusMatcher implements ItemMatcher {
	public ItemMobSpawnerSpawnRadiusMatcher(AmountMatcher spawnRadius) {
		this.spawnRadius = spawnRadius;
	}

	public AmountMatcher spawnRadius;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return spawnRadius.matches(MobSpawnerUtil.getMobSpawnerState(item).getSpawnRange());
	}
}
