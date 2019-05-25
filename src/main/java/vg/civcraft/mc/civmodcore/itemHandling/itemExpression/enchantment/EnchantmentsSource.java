package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Collections;
import java.util.Map;

/**
 * Represents the different holders of enchantments an item can have.
 *
 * @author Ameliorate
 */
public enum EnchantmentsSource {
	/**
	 * The normal enchantments on the item that take effect. For example a diamond sword with Sharpness 5.
	 */
	ITEM,

	/**
	 * For example from the enchantments held inside an enchanted book
	 */
	HELD;

	/**
	 * Gets the enchantments from an item based on the value of this.
	 * @param item The item to get the enchantments from.
	 * @return The stored enchantments if this == HELD, or the regular enchantments if this == ITEM.
	 */
	public Map<Enchantment, Integer> get(ItemStack item) {
		if (!item.hasItemMeta())
			return Collections.emptyMap();

		if (this == ITEM)
			return item.getEnchantments();

		if (this == HELD) {
			if (!(item.getItemMeta() instanceof EnchantmentStorageMeta))
				return Collections.emptyMap();
			return ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants();
		}

		throw new AssertionError("not reachable");
	}

	public void set(ItemStack item, Map<Enchantment, Integer> enchantments, boolean unsafe) {
		if (this == ITEM) {
			item.getEnchantments().keySet().forEach(item::removeEnchantment);
			if (unsafe)
				item.addUnsafeEnchantments(enchantments);
			else
				item.addEnchantments(enchantments);
		}

		if (this == HELD) {
			if (!(item.getItemMeta() instanceof EnchantmentStorageMeta))
				throw new IllegalArgumentException("item does not store enchantments");

			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();

			meta.getStoredEnchants().keySet().forEach(meta::removeStoredEnchant);
			enchantments.forEach((enchantment, level) ->
					meta.addStoredEnchant(enchantment, level, unsafe));
			item.setItemMeta(meta);
		}
	}

	public void add(ItemStack item, Enchantment enchantment, int level, boolean unsafe) {
		if (this == ITEM) {
			if (unsafe)
				item.addUnsafeEnchantment(enchantment, level);
			else
				item.addEnchantment(enchantment, level);
		}

		if (this == HELD) {
			if (!item.hasItemMeta() || !(item.getItemMeta() instanceof EnchantmentStorageMeta))
				throw new IllegalArgumentException("item does not store enchantments");

			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			meta.addStoredEnchant(enchantment, level, unsafe);
			item.setItemMeta(meta);
		}
	}

	/**
	 * @return A material that can reasonably hold enchantments in the slot according to this enum.
	 */
	public Material getReasonableType() {
		if (this == ITEM)
			return Material.WOODEN_SWORD;
		if (this == HELD)
			return Material.ENCHANTED_BOOK;

		throw new AssertionError("not reachable");
	}
}
