package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ExactlyEnchantment implements EnchantmentMatcher {
	public ExactlyEnchantment(Enchantment enchantment, AmountMatcher level) {
		this.enchantment = enchantment;
		this.level = level;
	}

	public Enchantment enchantment;
	public AmountMatcher level;

	@Override
	public boolean matches(Enchantment enchantment, int level) {
		if (!this.enchantment.equals(enchantment))
			return false;
		return this.level.matches(level);
	}
}
