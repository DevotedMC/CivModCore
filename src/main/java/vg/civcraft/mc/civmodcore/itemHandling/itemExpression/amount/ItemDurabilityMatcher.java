package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemDurabilityMatcher implements ItemMatcher {
	public ItemDurabilityMatcher(AmountMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemDurabilityMatcher construct(Optional<AmountMatcher> matcher) {
		return matcher.map(ItemDurabilityMatcher::new).orElse(null);
	}

	public AmountMatcher matcher;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof Damageable))
            return false;

		return matcher.matches(((Damageable) item.getItemMeta()).getDamage());
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());

		if (!(item instanceof Damageable))
			throw new NotSolvableException("item does not have durability");

		((Damageable) meta).setDamage(matcher.solve(1));
		item.setItemMeta(meta);

		return item;
	}
}
