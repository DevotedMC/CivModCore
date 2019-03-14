package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.ListMatchingMode;

import java.util.List;
import java.util.Map;

/**
 * @author Ameliorate
 */
public class ItemEnchantmentsMatcher implements ItemMatcher {

	public ItemEnchantmentsMatcher(List<EnchantmentMatcher> enchantmentMatchers, ListMatchingMode mode, EnchantmentsSource source) {
		if (enchantmentMatchers.isEmpty())
			throw new IllegalArgumentException("enchanmentMatchers can not be empty. If an empty enchantmentMatchers " +
					"was allowed, it would cause many subtle logic errors.");
		this.enchantmentMatchers = enchantmentMatchers;
		this.mode = mode;
		this.source = source;
	}

	public List<EnchantmentMatcher> enchantmentMatchers;
	public ListMatchingMode mode;
	public EnchantmentsSource source;

	@Override
	public boolean matches(ItemStack item) {
		switch (source) {
			case ITEM:
				return matches(item.getEnchantments());
			case HELD:
				if (!item.hasItemMeta() || !(item.getItemMeta() instanceof EnchantmentStorageMeta))
					return false;
				return matches(((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants());
		}
		throw new AssertionError("not reachable");
	}

	public boolean matches(Map<Enchantment, Integer> enchantments) {
		return mode.matches(enchantmentMatchers, enchantments.entrySet());
	}
}
