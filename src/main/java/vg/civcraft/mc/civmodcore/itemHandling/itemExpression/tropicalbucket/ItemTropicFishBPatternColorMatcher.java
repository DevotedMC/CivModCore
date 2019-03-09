package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.tropicalbucket;

import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.List;

/**
 * @author Ameliorate
 */
public class ItemTropicFishBPatternColorMatcher implements ItemMatcher {
	public ItemTropicFishBPatternColorMatcher(List<DyeColor> patternColor, boolean notInList) {
		this.patternColor = patternColor;
		this.notInList = notInList;
	}

	public ItemTropicFishBPatternColorMatcher(List<DyeColor> patternColor) {
		this(patternColor, false);
	}

	public List<DyeColor> patternColor;
	public boolean notInList;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof TropicalFishBucketMeta) ||
				!((TropicalFishBucketMeta) item.getItemMeta()).hasVariant())
			return false;

		if (notInList) {
			return !patternColor.contains(((TropicalFishBucketMeta) item.getItemMeta()).getPatternColor());
		} else {
			return patternColor.contains(((TropicalFishBucketMeta) item.getItemMeta()).getPatternColor());
		}
	}
}
