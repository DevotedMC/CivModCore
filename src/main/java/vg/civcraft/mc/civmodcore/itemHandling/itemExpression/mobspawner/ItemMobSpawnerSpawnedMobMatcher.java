package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
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
}
