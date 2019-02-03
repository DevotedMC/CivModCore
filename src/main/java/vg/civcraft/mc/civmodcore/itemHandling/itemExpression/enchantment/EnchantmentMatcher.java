package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;

/**
 * @author Ameliorate
 */
public interface EnchantmentMatcher {
	boolean matches(Enchantment enchantment, int level);
}
