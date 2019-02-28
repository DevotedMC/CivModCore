package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Collections;
import java.util.Map;

/**
 * Returns true when the item has exactly the list of enchantments, no fewer and no more.
 *
 * This is unlike ItemEnchantmentsMatcher with a bunch of ExactlyEnchantment's and mode: ALL, because
 * that will accept an item with extra enchantments. This will deny items with extra enchantments.
 *
 * @author Ameliorate
 */
public class ItemExactlyEnchantmentsMatcher implements ItemMatcher {
	public ItemExactlyEnchantmentsMatcher(Map<Enchantment, Integer> enchantments,
										  EnchantmentsSource source) {
		this.enchantments = enchantments;
		this.source = source;
	}

	public Map<Enchantment, Integer> enchantments;
	public EnchantmentsSource source;

	@Override
	public boolean matches(ItemStack item) {
		Map<Enchantment, Integer> itemEnchantments;
		switch (source) {
			case ITEM:
				itemEnchantments = item.getEnchantments();
				break;
			case HELD:
				if (!item.hasItemMeta() || !(item.getItemMeta() instanceof EnchantmentStorageMeta))
					itemEnchantments = Collections.emptyMap();
				else
					itemEnchantments = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants();
				break;
			default:
				throw new AssertionError("not reachable");
		}

		return itemEnchantments.equals(enchantments);
	}
}
