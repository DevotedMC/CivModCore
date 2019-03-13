package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

/**
 * @author Ameliorate
 */
public class ItemMaterialMatcher implements ItemMatcher {
	public ItemMaterialMatcher(MaterialMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemMaterialMatcher construct(MaterialMatcher matcher) {
		if (matcher == null)
			return null;

		return new ItemMaterialMatcher(matcher);
	}

	public MaterialMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
        return matcher.matches(item.getType());
	}
}
