package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

public class ItemFlagMatcher implements ItemMatcher {
	public ItemFlagMatcher(ItemFlag flag, boolean setting) {
		this.flag = flag;
		this.setting = setting;
	}

	public ItemFlag flag;
	public boolean setting;

	@Override
	public boolean matches(ItemStack item) {
		boolean setting;
		if (item.hasItemMeta()) {
			setting = item.getItemMeta().hasItemFlag(flag);
		} else {
			setting = false;
			// this is okay because all the flags default to false.
		}

		return this.setting == setting;
	}
}
