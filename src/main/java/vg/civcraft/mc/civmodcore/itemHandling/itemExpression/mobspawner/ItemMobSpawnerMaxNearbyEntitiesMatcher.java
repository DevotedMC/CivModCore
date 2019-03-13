package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.inventory.ItemStack;
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

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return maxNearbyEntities.matches(MobSpawnerUtil.getMobSpawnerState(item).getMaxNearbyEntities());
	}
}
