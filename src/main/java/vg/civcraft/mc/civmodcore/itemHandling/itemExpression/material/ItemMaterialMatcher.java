package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemMaterialMatcher implements ItemMatcher {
	public ItemMaterialMatcher(MaterialMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemMaterialMatcher construct(Optional<MaterialMatcher> matcher) {
		return matcher.map(ItemMaterialMatcher::new).orElse(null);
	}

	public MaterialMatcher matcher;

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
