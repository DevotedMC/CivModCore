package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.firework;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class ItemFireworkPowerMatcher implements ItemMatcher {
	public ItemFireworkPowerMatcher(AmountMatcher power) {
		this.power = power;
	}

	public AmountMatcher power;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof FireworkMeta))
			return false;

		return power.matches(((FireworkMeta) item.getItemMeta()).getPower());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof FireworkMeta)) {
			item.setType(Material.FIREWORK_ROCKET);
		}

		assert item.getItemMeta() instanceof FireworkMeta;

		FireworkMeta meta = (FireworkMeta) item.getItemMeta();
		meta.setPower(power.solve(1));
		item.setItemMeta(meta);
		return item;
	}
}
