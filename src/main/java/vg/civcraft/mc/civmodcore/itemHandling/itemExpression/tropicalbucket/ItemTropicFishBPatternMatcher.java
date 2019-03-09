package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.tropicalbucket;

import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.List;

/**
 * @author Ameliorate
 */
public class ItemTropicFishBPatternMatcher implements ItemMatcher {
	public ItemTropicFishBPatternMatcher(List<TropicalFish.Pattern> pattern, boolean notInList) {
		this.pattern = pattern;
		this.notInList = notInList;
	}

	public ItemTropicFishBPatternMatcher(List<TropicalFish.Pattern> pattern) {
		this(pattern, false);
	}

	public List<TropicalFish.Pattern> pattern;
	public boolean notInList;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof TropicalFishBucketMeta) ||
				!((TropicalFishBucketMeta) item.getItemMeta()).hasVariant())
			return false;

		if (notInList) {
			return !pattern.contains(((TropicalFishBucketMeta) item.getItemMeta()).getPattern());
		} else {
			return pattern.contains(((TropicalFishBucketMeta) item.getItemMeta()).getPattern());
		}
	}
}
