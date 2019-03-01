package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * Matches any enchantment type, as long as the level matches the level matcher.
 *
 * @author Ameliorate
 */
public class AnyEnchantment implements EnchantmentMatcher {
	public AnyEnchantment(AmountMatcher level) {
		this.level = level;
	}

	public AmountMatcher level;

	@Override
	public boolean matches(Enchantment enchantment, int level) {
		return this.level.matches(level);
	}
}
