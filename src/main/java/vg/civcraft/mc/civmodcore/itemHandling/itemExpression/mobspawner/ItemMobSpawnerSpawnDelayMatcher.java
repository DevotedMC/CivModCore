package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
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

	private final int DEFAULT_MIN_SPAWN_DELAY = 200; // ticks, 10 seconds
	private final int DEFAULT_MAX_SPAWN_DELAY = 799; // ticks, 39.95 seconds

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		CreatureSpawner spawner = MobSpawnerUtil.getMobSpawnerState(item);
		int spawnDelay = source == MinMax.MAX ? spawner.getMaxSpawnDelay() : spawner.getMinSpawnDelay();

		return this.spawnDelay.matches(spawnDelay);
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		MobSpawnerUtil.setToMobSpawner(item);
		CreatureSpawner spawner = MobSpawnerUtil.getMobSpawnerState(item);

		switch (source) {
			case MIN:
				spawner.setMinSpawnDelay(spawnDelay.solve(DEFAULT_MIN_SPAWN_DELAY));
				break;
			case MAX:
				spawner.setMaxSpawnDelay(spawnDelay.solve(DEFAULT_MAX_SPAWN_DELAY));
				break;
		}

		BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
		meta.setBlockState(spawner);
		item.setItemMeta(meta);

		return item;
	}

	public enum MinMax {
		MIN,
		MAX,
	}
}
