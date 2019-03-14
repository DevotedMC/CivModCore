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
		return mode.matches(potionMatchers, effects);
	}
}
