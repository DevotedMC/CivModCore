package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.potion;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ExactlyPotionEffect implements PotionEffectMatcher {
	public ExactlyPotionEffect(PotionEffectType type, AmountMatcher level, AmountMatcher duration) {
		this.type = type;
		this.level = level;
		this.duration = duration;
	}

	public PotionEffectType type;
	public AmountMatcher level;
	public AmountMatcher duration;

	@Override
	public boolean matches(PotionEffect effect) {
		return type.equals(effect.getType()) &&
				level.matches(effect.getAmplifier()) &&
				duration.matches(effect.getDuration());
	}
}
