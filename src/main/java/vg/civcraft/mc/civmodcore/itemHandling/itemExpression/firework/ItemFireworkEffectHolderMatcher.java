package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.firework;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * @author Ameliorate
 */
public class ItemFireworkEffectHolderMatcher implements ItemMatcher {
	public ItemFireworkEffectHolderMatcher(FireworkEffectMatcher effect) {
		this.effect = effect;
	}

	public static ItemFireworkEffectHolderMatcher construct(FireworkEffectMatcher effect) {
		if (effect != null)
			return null;

		return new ItemFireworkEffectHolderMatcher(effect);
	}

	public FireworkEffectMatcher effect;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof FireworkEffectMeta) ||
				!((FireworkEffectMeta) item.getItemMeta()).hasEffect())
			return false;

		return effect.matches(((FireworkEffectMeta) item.getItemMeta()).getEffect());
	}
}
