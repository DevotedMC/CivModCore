package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;

/**
 * @author Ameliorate
 */
public class ItemMobSpawnerSpawnedMobMatcher implements ItemMatcher {
	public ItemMobSpawnerSpawnedMobMatcher(EnumMatcher<EntityType> spawned) {
		this.spawned = spawned;
	}

	public EnumMatcher<EntityType> spawned;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return spawned.matches(MobSpawnerUtil.getMobSpawnerState(item).getSpawnedType());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		MobSpawnerUtil.setToMobSpawner(item);
		CreatureSpawner spawner = MobSpawnerUtil.getMobSpawnerState(item);

		spawner.setSpawnedType(spawned.solve(EntityType.PIG));

		BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
		meta.setBlockState(spawner);
		item.setItemMeta(meta);

		return item;
	}
}
