package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

import java.util.Map;

/**
 * @author Ameliorate
 */
public interface EnchantmentMatcher extends GenericMatcher<Map.Entry<Enchantment, Integer>> {
	boolean matches(Enchantment enchantment, int level);

	@Override
	default boolean genericMatches(Map.Entry<Enchantment, Integer> matched) {
		return matches(matched.getKey(), matched.getValue());
	}
}
