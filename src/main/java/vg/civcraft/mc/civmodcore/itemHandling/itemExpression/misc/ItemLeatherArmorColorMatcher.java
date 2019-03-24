package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.color.ColorMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemLeatherArmorColorMatcher implements ItemMatcher {
	public ItemLeatherArmorColorMatcher(ColorMatcher color) {
		this.color = color;
	}

	public static ItemLeatherArmorColorMatcher construct(Optional<ColorMatcher> color) {
		return color.map(ItemLeatherArmorColorMatcher::new).orElse(null);
	}

	public ColorMatcher color;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof LeatherArmorMeta))
			return false;

		Color leatherColor = ((LeatherArmorMeta) item.getItemMeta()).getColor();
		return color.matches(leatherColor);
	}
}
