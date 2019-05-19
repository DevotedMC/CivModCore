package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemShulkerBoxColorMatcher implements ItemMatcher {
	public ItemShulkerBoxColorMatcher(EnumMatcher<DyeColor> color) {
		this.color = color;
	}

	public static ItemShulkerBoxColorMatcher construct(Optional<EnumMatcher<DyeColor>> color) {
		return color.map(ItemShulkerBoxColorMatcher::new).orElse(null);
	}

	public EnumMatcher<DyeColor> color;

	@Override
	public boolean matches(ItemStack item) {
		if (!colorsShulkerBox.containsKey(item.getType()))
			return false;

		return this.color.matches(colorsShulkerBox.get(item.getType()));
	}

	private static BiMap<DyeColor, Material> shulkerBoxColors = HashBiMap.create();
	private static BiMap<Material, DyeColor> colorsShulkerBox;

	static {
		shulkerBoxColors.put(DyeColor.BLACK, Material.BLACK_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.BLUE, Material.BLUE_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.BROWN, Material.BROWN_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.CYAN, Material.CYAN_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.GRAY, Material.GRAY_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.GREEN, Material.GREEN_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.LIGHT_BLUE, Material.LIGHT_BLUE_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.LIGHT_GRAY, Material.LIGHT_GRAY_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.LIME, Material.LIME_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.MAGENTA, Material.MAGENTA_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.ORANGE, Material.ORANGE_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.PINK, Material.PINK_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.PURPLE, Material.PURPLE_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.RED, Material.RED_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.WHITE, Material.WHITE_SHULKER_BOX);
		shulkerBoxColors.put(DyeColor.YELLOW, Material.YELLOW_SHULKER_BOX);

		colorsShulkerBox = shulkerBoxColors.inverse();
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		DyeColor color = this.color.solve(DyeColor.PURPLE);
		item.setType(shulkerBoxColors.get(color));
		return item;
	}
}
