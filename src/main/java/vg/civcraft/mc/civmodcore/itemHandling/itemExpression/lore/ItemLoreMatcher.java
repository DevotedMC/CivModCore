package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Collections;
import java.util.List;

/**
 * @author Ameliorate
 */
public class ItemLoreMatcher implements ItemMatcher {
	public ItemLoreMatcher(LoreMatcher matcher) {
		this.matcher = matcher;
	}

	public LoreMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		List<String> itemLore;
		if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
			itemLore = Collections.emptyList();
		else
			itemLore = item.getItemMeta().getLore();

		return matcher.matches(itemLore);
	}
}
