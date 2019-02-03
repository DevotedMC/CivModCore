package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;

/**
 * @author Ameliorate
 */
public class AnyEnchantment implements EnchantmentMatcher {
	@Override
	public boolean matches(Enchantment enchantment, int level) {
		return true;
	}
}
