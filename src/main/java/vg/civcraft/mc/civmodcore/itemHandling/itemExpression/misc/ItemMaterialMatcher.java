package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemMaterialMatcher implements ItemMatcher {
	public ItemMaterialMatcher(EnumMatcher<Material> matcher) {
		this.matcher = matcher;
	}

	public static ItemMaterialMatcher construct(Optional<EnumMatcher<Material>> matcher) {
		return matcher.map(ItemMaterialMatcher::new).orElse(null);
	}

	public EnumMatcher<Material> matcher;

	@Override
	public boolean matches(ItemStack item) {
        return matcher.matches(item.getType());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		item.setType(matcher.solve(item.getType()));
		return item;
	}
}
