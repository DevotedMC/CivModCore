package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
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

	private final int DEFAULT_SPAWN_RADIUS = 3;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return spawnRadius.matches(MobSpawnerUtil.getMobSpawnerState(item).getSpawnRange());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		MobSpawnerUtil.setToMobSpawner(item);
		CreatureSpawner spawner = MobSpawnerUtil.getMobSpawnerState(item);

		spawner.setSpawnRange(spawnRadius.solve(DEFAULT_SPAWN_RADIUS));

		BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
		meta.setBlockState(spawner);
		item.setItemMeta(meta);

		return item;
	}
}
