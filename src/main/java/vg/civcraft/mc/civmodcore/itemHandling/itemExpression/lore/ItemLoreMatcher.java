package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemLoreMatcher implements ItemMatcher {
	public ItemLoreMatcher(LoreMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemLoreMatcher construct(Optional<LoreMatcher> matcher) {
		return matcher.map(ItemLoreMatcher::new).orElse(null);
	}

	public LoreMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
            return false;

		return matcher.matches(item.getItemMeta().getLore());
	}
}
