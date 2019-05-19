package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.tropicalbucket;

import org.bukkit.Material;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemTropicFishBPatternMatcher implements ItemMatcher {
	public ItemTropicFishBPatternMatcher(EnumMatcher<TropicalFish.Pattern> pattern) {
		this.pattern = pattern;
	}

	public static ItemTropicFishBPatternMatcher construct(Optional<EnumMatcher<TropicalFish.Pattern>> color) {
		return color.map(ItemTropicFishBPatternMatcher::new).orElse(null);
	}

	public EnumMatcher<TropicalFish.Pattern> pattern;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof TropicalFishBucketMeta) ||
				!((TropicalFishBucketMeta) item.getItemMeta()).hasVariant())
			return false;

		return pattern.matches(((TropicalFishBucketMeta) item.getItemMeta()).getPattern());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof TropicalFishBucketMeta))
			item.setType(Material.TROPICAL_FISH_BUCKET);

		TropicalFishBucketMeta meta = (TropicalFishBucketMeta) item.getItemMeta();

		meta.setPattern(pattern.solve(meta.getPattern()));
		item.setItemMeta(meta);
		return item;
	}
}
