package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.firework;

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.ListMatchingMode;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Ameliorate
 */
public class ItemFireworkEffectsMatcher implements ItemMatcher {
	public ItemFireworkEffectsMatcher(List<FireworkEffectMatcher> effects, ListMatchingMode mode) {
		this.effects = effects;
		this.mode = mode;
	}

	public List<FireworkEffectMatcher> effects;
	public ListMatchingMode mode;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof FireworkMeta) ||
				!((FireworkMeta) item.getItemMeta()).hasEffects())
			return false;

		List<FireworkEffect> effects = ((FireworkMeta) item.getItemMeta()).getEffects();
		return mode.matches(this.effects, effects);
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof FireworkMeta))
			item.setType(Material.FIREWORK_ROCKET);

		FireworkMeta meta = (FireworkMeta) item.getItemMeta();
		if (mode == ListMatchingMode.NONE) {
			meta.clearEffects();
			// we clear effects because there may be in there an effectmatcher that matches an effect in effects
			// except, this is pointless because ListMatchingMode can't solve a NONE.
		}

		List<FireworkEffect> effects = mode.solve(this.effects, new Supplier<FireworkEffect>() {
			public int index = 0;

			@Override
			public FireworkEffect get() {
				return ItemFireworkEffectsCountMatcher.getFireworkEffectWithIndex(index++);
			}
		}); // we use the old way of lambdas so we can have the index thing there.

		meta.addEffects(effects);
		item.setItemMeta(meta);
		return item;
	}
}
