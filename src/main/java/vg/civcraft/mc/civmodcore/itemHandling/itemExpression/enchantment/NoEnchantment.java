package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;

/**
 * @author Ameliorate
 */
public class NoEnchantment implements EnchantmentMatcher {
	@Override
	public boolean matches(Enchantment enchantment, int level) {
		return false;
	}
}
