package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.potion;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.ListMatchingMode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Ameliorate
 */
public class ItemPotionEffectsMatcher implements ItemMatcher {
	public ItemPotionEffectsMatcher(List<PotionEffectMatcher> potionMatchers, ListMatchingMode mode) {
		if (potionMatchers.isEmpty())
			throw new IllegalArgumentException("potionMatchers can not be empty. If an empty potionMatchers " +
					"was allowed, it would cause many subtle logic errors.");
		this.potionMatchers = potionMatchers;
		this.mode = mode;
	}

	public List<PotionEffectMatcher> potionMatchers;
	public ListMatchingMode mode;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof PotionMeta))
			return false;

		PotionMeta potion = (PotionMeta) item.getItemMeta();

		List<PotionEffect> effects = new ArrayList<>();
		if (potion.hasCustomEffects())
			effects.addAll(potion.getCustomEffects());

		return matches(effects);
	}

	public boolean matches(List<PotionEffect> effects) {
		Stream<PotionEffectMatcher> enchantmentMatcherStream = potionMatchers.stream();
		Predicate<PotionEffectMatcher> predicate = (potionMatcher) -> effects.stream().anyMatch(potionMatcher::matches);

		switch (mode) {
			// Normally there'd be a break statement after each of the return's, but java complains because the break's
			// are technically unreachable.
			case ANY:
				return enchantmentMatcherStream.anyMatch(predicate);
			case ALL:
				return enchantmentMatcherStream.allMatch(predicate);
			case NONE:
				return enchantmentMatcherStream.noneMatch(predicate);
		}

		throw new AssertionError("not reachable");
		// naturally, it complains here because it can't figure out that the switch above always returns, so we don't
		// need a return statement here.
	}
}
