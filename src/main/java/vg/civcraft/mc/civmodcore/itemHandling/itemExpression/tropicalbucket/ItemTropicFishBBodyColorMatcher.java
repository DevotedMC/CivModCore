package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.tropicalbucket;

import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.List;

/**
 * @author Ameliorate
 */
public class ItemTropicFishBBodyColorMatcher implements ItemMatcher {
	public ItemTropicFishBBodyColorMatcher(List<DyeColor> bodyColor) {
		this(bodyColor, false);
	}

	public ItemTropicFishBBodyColorMatcher(List<DyeColor> bodyColor, boolean notInList) {
		this.bodyColor = bodyColor;
		this.notInList = notInList;
	}

	public List<DyeColor> bodyColor;
	public boolean notInList;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof TropicalFishBucketMeta) ||
				!((TropicalFishBucketMeta) item.getItemMeta()).hasVariant())
			return false;

		if (notInList) {
			return !bodyColor.contains(((TropicalFishBucketMeta) item.getItemMeta()).getBodyColor());
		} else {
			return bodyColor.contains(((TropicalFishBucketMeta) item.getItemMeta()).getBodyColor());
		}
	}
}
