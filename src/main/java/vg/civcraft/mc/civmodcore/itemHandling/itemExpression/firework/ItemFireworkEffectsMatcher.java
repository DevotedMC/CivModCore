package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.firework;

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.ListMatchingMode;

import java.util.List;

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
}
