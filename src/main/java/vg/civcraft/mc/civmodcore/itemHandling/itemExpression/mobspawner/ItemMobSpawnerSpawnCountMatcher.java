package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
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

	private final int DEFAULT_SPAWN_COUNT = 4;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return spawnCount.matches(MobSpawnerUtil.getMobSpawnerState(item).getSpawnCount());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		MobSpawnerUtil.setToMobSpawner(item);
		CreatureSpawner spawner = MobSpawnerUtil.getMobSpawnerState(item);

		spawner.setSpawnCount(spawnCount.solve(DEFAULT_SPAWN_COUNT));

		BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
		meta.setBlockState(spawner);
		item.setItemMeta(meta);

		return item;
	}
}
