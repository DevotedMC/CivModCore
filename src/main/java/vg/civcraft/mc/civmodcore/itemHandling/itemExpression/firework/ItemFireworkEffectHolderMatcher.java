package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.firework;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemFireworkEffectHolderMatcher implements ItemMatcher {
	public ItemFireworkEffectHolderMatcher(FireworkEffectMatcher effect) {
		this.effect = effect;
	}

	public static ItemFireworkEffectHolderMatcher construct(Optional<FireworkEffectMatcher> effect) {
		return effect.map(ItemFireworkEffectHolderMatcher::new).orElse(null);
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
