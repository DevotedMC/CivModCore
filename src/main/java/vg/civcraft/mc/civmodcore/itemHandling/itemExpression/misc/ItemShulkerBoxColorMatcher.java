package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.DyeColor;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.List;

/**
 * @author Ameliorate
 */
public class ItemShulkerBoxColorMatcher implements ItemMatcher {
	public ItemShulkerBoxColorMatcher(List<DyeColor> color, boolean notInList) {
		this.color = color;
		this.notInList = notInList;
	}

	public ItemShulkerBoxColorMatcher(List<DyeColor> color) {
		this(color, false);
	}

	public List<DyeColor> color;
	public boolean notInList;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof BlockStateMeta) ||
				!((BlockStateMeta) item.getItemMeta()).hasBlockState() ||
				!(((BlockStateMeta) item.getItemMeta()).getBlockState() instanceof ShulkerBox))
			return false;

		DyeColor colour = ((ShulkerBox) ((BlockStateMeta) item.getItemMeta()).getBlockState()).getColor();

		if (notInList) {
			return !this.color.contains(colour);
		} else {
			return this.color.contains(colour);
		}
	}
}
