package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.firework;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.color.ColorMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.ListMatchingMode;

import java.util.List;
import java.util.Optional;

/**
 * @author Amleiorate
 */
public class ExactlyFireworkEffect implements FireworkEffectMatcher {
	public ExactlyFireworkEffect(EnumMatcher<FireworkEffect.Type> type,
								 List<ColorMatcher> colors, ListMatchingMode colorsMode,
								 List<ColorMatcher> fadeColors, ListMatchingMode fadeColorsMode,
								 Optional<Boolean> hasFlicker, Optional<Boolean> hasTrail) {
		this.type = type;
		this.colors = colors;
		this.colorsMode = colorsMode;
		this.fadeColors = fadeColors;
		this.fadeColorsMode = fadeColorsMode;
		this.hasFlicker = hasFlicker;
		this.hasTrail = hasTrail;
	}

	public EnumMatcher<FireworkEffect.Type> type;

	public List<ColorMatcher> colors;
	public ListMatchingMode colorsMode;

	public List<ColorMatcher> fadeColors;
	public ListMatchingMode fadeColorsMode;

	public Optional<Boolean> hasFlicker = Optional.empty();
	public Optional<Boolean> hasTrail = Optional.empty();

	@Override
	public boolean matches(FireworkEffect effect) {
		if (hasFlicker.isPresent()) {
			if (hasFlicker.get() != effect.hasFlicker())
				return false;
		}

		if (hasTrail.isPresent()) {
			if (hasTrail.get() != effect.hasTrail())
				return false;
		}

		if (type != null && !type.matches(effect.getType()))
			return false;

		if (!colorsMode.matches(colors, effect.getColors()))
			return false;

		if (!fadeColorsMode.matches(fadeColors, effect.getFadeColors()))
			return false;

		return true;
	}

	@Override
	public FireworkEffect solve(FireworkEffect effect) throws NotSolvableException {
		FireworkEffect.Type type = this.type.solve(effect.getType());
		List<Color> colors = colorsMode.solve(this.colors, () -> Color.WHITE);
		List<Color> fadeColors = fadeColorsMode.solve(this.fadeColors, () -> Color.WHITE);
		boolean hasFlicker = this.hasFlicker.orElse(effect.hasFlicker());
		boolean hasTrail = this.hasTrail.orElse(effect.hasTrail());

		return FireworkEffect.builder()
				.with(type)
				.withColor(colors)
				.withFade(fadeColors)
				.flicker(hasFlicker)
				.trail(hasTrail)
				.build();
	}
}
