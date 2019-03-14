package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.potion;

import org.bukkit.potion.PotionEffect;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

/**
 * @author Ameliorate
 */
public interface PotionEffectMatcher extends GenericMatcher<PotionEffect> {
	boolean matches(PotionEffect effect);

	@Override
	default boolean genericMatches(PotionEffect matched) {
		return matches(matched);
	}
}
