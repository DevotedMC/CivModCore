package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.DyeColor;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;

/**
 * @author Ameliorate
 */
public class ItemShulkerBoxColorMatcher implements ItemMatcher {
	public ItemShulkerBoxColorMatcher(EnumMatcher<DyeColor> color) {
		this.color = color;
	}

	public EnumMatcher<DyeColor> color;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BlockStateMeta) ||
				!((BlockStateMeta) item.getItemMeta()).hasBlockState() ||
				!(((BlockStateMeta) item.getItemMeta()).getBlockState() instanceof ShulkerBox))
			return false;

		DyeColor colour = ((ShulkerBox) ((BlockStateMeta) item.getItemMeta()).getBlockState()).getColor();

		return this.color.matches(colour);
	}
}
