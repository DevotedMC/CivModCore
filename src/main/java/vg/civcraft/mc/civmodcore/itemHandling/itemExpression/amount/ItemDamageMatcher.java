package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.Optional;

/**
 * @author Ameliorate
 */
public class ItemDamageMatcher implements ItemMatcher {
	public ItemDamageMatcher(AmountMatcher matcher) {
		this.matcher = matcher;
	}

	public static ItemDamageMatcher construct(Optional<AmountMatcher> matcher) {
		return matcher.map(ItemDamageMatcher::new).orElse(null);
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
		ItemMeta meta = item.getItemMeta();

		if (!(meta instanceof Damageable))
			throw new NotSolvableException("item does not have durability");

		((Damageable) meta).setDamage(matcher.solve(1));
		item.setItemMeta(meta);

		return item;
	}
}
