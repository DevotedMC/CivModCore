package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.potion;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enummatcher.EnumMatcher;

import java.util.Optional;

/**
 * Matches the "base" effect of a potion.
 *
 * A potion obtainable in vanilla survival singleplayer is represented by this class, storing only the type,
 * and a boolean of if it's upgraded and if it's extended.
 *
 * Notably, this class as well as vanilla does not expose a duration. Instead the duration is calculated from the
 * type and extended boolean.
 *
 * @author Ameliorate
 */
public class ItemPotionBaseEffectMatcher implements ItemMatcher {
	public ItemPotionBaseEffectMatcher(EnumMatcher<PotionType> type, Optional<Boolean> isExtended, Optional<Boolean> isUpgraded) {
		this.type = type;
		this.isExtended = isExtended;
		this.isUpgraded = isUpgraded;
	}

	public EnumMatcher<PotionType> type;
	public Optional<Boolean> isExtended;
	public Optional<Boolean> isUpgraded;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof PotionMeta))
			return false;

		PotionData data = ((PotionMeta) item.getItemMeta()).getBasePotionData();

		PotionType type = data.getType();
		boolean extended = data.isExtended();
		boolean upgraded = data.isUpgraded();

		if (isExtended.isPresent()) {
			if (extended != isExtended.get())
				return false;
		}

		if (isUpgraded.isPresent()) {
			if (upgraded != isUpgraded.get())
				return false;
		}

		return this.type.matches(type);
	}
}
