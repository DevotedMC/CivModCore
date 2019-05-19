package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.mobspawner;

import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

/**
 * Utility class for dealing with Creature Spawners.
 *
 * This is mostly ment for ItemExpressions matching over mob spawners.
 *
 * @author Ameliorate
 */
public class MobSpawnerUtil {
	/**
	 * Checks if a given item holds a BlockState, and if it does if that BlockState holds a CreatureSpawner.
	 *
	 * @param item The item that may or may not hold a CreatureSpawner
	 * @return true if the item holds a CreatureSpawner, false otherwise
	 */
	public static boolean isMobSpawner(ItemStack item) {
		if (!item.hasItemMeta())
			return false;

		if (!(item.getItemMeta() instanceof BlockStateMeta))
			return false;

		if (!((BlockStateMeta) item.getItemMeta()).hasBlockState())
			return false;

		return ((BlockStateMeta) item.getItemMeta()).getBlockState() instanceof CreatureSpawner;
	}

	/**
	 * Gets a CreatureSpawner from an ItemStack, by getting a stored BlockState from the item's meta, and then
	 * casting that BlockState to a CreatureSpawner.
	 *
	 * @param item The item to get a CreatureSpawner from.
	 * @return The CreatureSpawner the ItemStack was holding.
	 * @throws IllegalArgumentException If the item does not hold a CreatureSpawner
	 */
	public static CreatureSpawner getMobSpawnerState(ItemStack item) {
		if (!isMobSpawner(item))
			throw new IllegalArgumentException("item is not a mob spawner");

		BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();

		return (CreatureSpawner) meta.getBlockState();
	}

	public static void setToMobSpawner(ItemStack item) {
		if (isMobSpawner(item))
			return;

		item.setType(Material.SPAWNER);
	}
}
