package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.potion;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * Matches any potion type, as long as the level matches the level matcher.
 *
 * @author Ameliorate
 */
public class AnyPotionEffect implements PotionEffectMatcher {
	public AnyPotionEffect(AmountMatcher level, AmountMatcher duration) {
		this.level = level;
		this.duration = duration;
	}

	public AmountMatcher level;
	public AmountMatcher duration;

	@Override
	public boolean matches(PotionEffect effect) {
		return level.matches(effect.getAmplifier()) && duration.matches(effect.getDuration());
	}

	@Override
	public PotionEffect solve(PotionEffect defaultValue) throws NotSolvableException {
		return new PotionEffect(PotionEffectType.HEAL, 0, 0);
	}
}
