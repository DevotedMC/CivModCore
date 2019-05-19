package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ItemMobSpawnerMaxNearbyEntitiesMatcher implements ItemMatcher {
	public ItemMobSpawnerMaxNearbyEntitiesMatcher(AmountMatcher maxNearbyEntities) {
		this.maxNearbyEntities = maxNearbyEntities;
	}

	public AmountMatcher maxNearbyEntities;

	private final int DEFAULT_MAX_NEARBY_ENTITIES = 6;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return maxNearbyEntities.matches(MobSpawnerUtil.getMobSpawnerState(item).getMaxNearbyEntities());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		MobSpawnerUtil.setToMobSpawner(item);
		CreatureSpawner spawner = MobSpawnerUtil.getMobSpawnerState(item);

		spawner.setMaxNearbyEntities(maxNearbyEntities.solve(DEFAULT_MAX_NEARBY_ENTITIES));

		BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
		meta.setBlockState(spawner);
		item.setItemMeta(meta);

		return item;
	}
}
