package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ItemMobSpawnerSpawnDelayMatcher implements ItemMatcher {
	public ItemMobSpawnerSpawnDelayMatcher(AmountMatcher spawnDelay, MinMax source) {
		this.spawnDelay = spawnDelay;
		this.source = source;
	}

	public AmountMatcher spawnDelay;
	public MinMax source;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		CreatureSpawner spawner = MobSpawnerUtil.getMobSpawnerState(item);
		int spawnDelay = source == MinMax.MAX ? spawner.getMaxSpawnDelay() : spawner.getMinSpawnDelay();

		return this.spawnDelay.matches(spawnDelay);
	}

	public enum MinMax {
		MIN,
		MAX,
	}
}
