package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Ameliorate
 */
public class ItemEnchantmentsMatcher implements ItemMatcher {
	public enum Mode {
		ANY,
		ALL,
		NONE,
	}

	public ItemEnchantmentsMatcher(List<EnchantmentMatcher> enchantmentMatchers, Mode mode, EnchantmentsSource source) {
		this.enchantmentMatchers = enchantmentMatchers;
		this.mode = mode;
		this.source = source;
	}

	public List<EnchantmentMatcher> enchantmentMatchers;
	public Mode mode;
	public EnchantmentsSource source;

	@Override
	public boolean matches(ItemStack item) {
		switch (source) {
			case ITEM:
				return matches(item.getEnchantments());
			case HELD:
				if (!item.hasItemMeta() || !(item.getItemMeta() instanceof EnchantmentStorageMeta))
					return false;
				return matches(((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants());
		}
		throw new AssertionError("not reachable");
		// see the below function for the coy remarks
	}

	public boolean matches(Map<Enchantment, Integer> enchantments) {
		Stream<EnchantmentMatcher> enchantmentMatcherStream = enchantmentMatchers.stream();
		Predicate<EnchantmentMatcher> predicate = (enchantmentMatcher) -> enchantments.entrySet().stream().anyMatch((e) ->  {
			Enchantment enchantment = e.getKey();
			int level = e.getValue();
			return enchantmentMatcher.matches(enchantment, level);
		});

		switch (mode) {
			// Normally there'd be a break statement after each of the return's, but java complains because the break's
			// are technically unreachable.
			case ANY:
				return enchantmentMatcherStream.anyMatch(predicate);
			case ALL:
				return enchantmentMatcherStream.allMatch(predicate);
			case NONE:
				return enchantmentMatcherStream.noneMatch(predicate);
		}

		throw new AssertionError("not reachable");
		// naturally, it complains here because it can't figure out that the switch above always returns, so we don't
		// need a return statement here.
	}
}
