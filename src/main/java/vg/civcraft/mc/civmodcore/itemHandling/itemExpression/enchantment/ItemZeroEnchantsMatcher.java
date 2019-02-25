package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Collections;
import java.util.Map;

/**
 * Returns false if the item has any enchantments in the specified field.
 *
 * @author Ameliorate
 */
public class ItemZeroEnchantsMatcher implements ItemMatcher {
	/**
	 * Defaults the source to EnchantmentSource.ITEM.
	 */
	public ItemZeroEnchantsMatcher() {
		this(EnchantmentsSource.ITEM);
	}

	public ItemZeroEnchantsMatcher(EnchantmentsSource source) {
		this.source = source;
	}

	public EnchantmentsSource source;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta())
			return true;

		Map<Enchantment, Integer> enchantments = Collections.emptyMap();
		switch (source) {
			case ITEM:
				enchantments = item.getEnchantments();
				break;
			case HELD:
				if (!(item.getItemMeta() instanceof EnchantmentStorageMeta))
					enchantments = Collections.emptyMap();
				else
					enchantments = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants();
				break;
		}

		return enchantments.isEmpty();
	}
}
