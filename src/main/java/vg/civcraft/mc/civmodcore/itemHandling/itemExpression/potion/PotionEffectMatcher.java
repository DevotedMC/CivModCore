package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.potion;

import org.bukkit.potion.PotionEffect;

/**
 * @author Ameliorate
 */
public interface PotionEffectMatcher {
	boolean matches(PotionEffect effect);
}
