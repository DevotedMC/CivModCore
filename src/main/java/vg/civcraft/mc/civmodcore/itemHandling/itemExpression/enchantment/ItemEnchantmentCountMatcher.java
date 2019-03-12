package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ItemEnchantmentCountMatcher implements ItemMatcher {
	public ItemEnchantmentCountMatcher(AmountMatcher enchantmentCount, EnchantmentsSource source) {
		this.enchantmentCount = enchantmentCount;
		this.source = source;
	}

	public ItemEnchantmentCountMatcher(AmountMatcher enchantmentCount) {
		this(enchantmentCount, EnchantmentsSource.ITEM);
	}

	public AmountMatcher enchantmentCount;
	public EnchantmentsSource source;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta())
			return false;
		if (source == EnchantmentsSource.HELD && !(item.getItemMeta() instanceof EnchantmentStorageMeta))
			return false;

		int count = 0;
		switch (source) {
			case HELD:
				count = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants().size();
				break;
			case ITEM:
				count = item.getItemMeta().getEnchants().size();
				break;
		}

		return enchantmentCount.matches(count);
	}
}
