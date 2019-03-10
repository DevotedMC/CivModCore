package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.tropicalbucket;

import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;

/**
 * @author Ameliorate
 */
public class ItemTropicFishBPatternMatcher implements ItemMatcher {
	public ItemTropicFishBPatternMatcher(EnumMatcher<TropicalFish.Pattern> pattern) {
		this.pattern = pattern;
	}

	public EnumMatcher<TropicalFish.Pattern> pattern;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof TropicalFishBucketMeta) ||
				!((TropicalFishBucketMeta) item.getItemMeta()).hasVariant())
			return false;

		return pattern.matches(((TropicalFishBucketMeta) item.getItemMeta()).getPattern());
	}
}
