package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Amelorate
 */
public class ItemMobSpawnerDelayMatcher implements ItemMatcher {
	public ItemMobSpawnerDelayMatcher(AmountMatcher delay) {
		this.delay = delay;
	}

	public AmountMatcher delay;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return delay.matches(MobSpawnerUtil.getMobSpawnerState(item).getDelay());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		MobSpawnerUtil.setToMobSpawner(item);
		CreatureSpawner spawner = MobSpawnerUtil.getMobSpawnerState(item);

		spawner.setDelay(delay.solve(0));

		BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
		meta.setBlockState(spawner);
		item.setItemMeta(meta);

		return item;
	}
}
