package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemNameMatcher implements ItemMatcher {
	public ItemNameMatcher(NameMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemNameMatcher construct(Optional<NameMatcher> matcher) {
		return matcher.map(ItemNameMatcher::new).orElse(null);
	}

	public NameMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		String name;
		if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName())
			name = "";
		else
			name = item.getItemMeta().getDisplayName();
		return matcher.matches(name);
	}
}
