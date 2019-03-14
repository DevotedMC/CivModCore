package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.Matcher;

import java.util.Map;

/**
 * @author Ameliorate
 */
public interface EnchantmentMatcher extends Matcher<Map.Entry<Enchantment, Integer>> {
	boolean matches(Enchantment enchantment, int level);

	@Override
	default boolean matches(Map.Entry<Enchantment, Integer> matched) {
		return matches(matched.getKey(), matched.getValue());
	}
}
