package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ItemMobSpawnerRequiredPlayerRangeMatcher implements ItemMatcher {
	public ItemMobSpawnerRequiredPlayerRangeMatcher(AmountMatcher range) {
		this.range = range;
	}

	public AmountMatcher range;

	private final int DEFAULT_REQUIRED_PLAYER_RANGE = 16;

	@Override
	public boolean matches(ItemStack item) {
		if (!MobSpawnerUtil.isMobSpawner(item))
			return false;

		return range.matches(MobSpawnerUtil.getMobSpawnerState(item).getRequiredPlayerRange());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		MobSpawnerUtil.setToMobSpawner(item);
		CreatureSpawner spawner = MobSpawnerUtil.getMobSpawnerState(item);

		spawner.setRequiredPlayerRange(range.solve(DEFAULT_REQUIRED_PLAYER_RANGE));

		BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
		meta.setBlockState(spawner);
		item.setItemMeta(meta);

		return item;
	}
}
