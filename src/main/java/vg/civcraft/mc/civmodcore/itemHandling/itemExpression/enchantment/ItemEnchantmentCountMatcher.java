package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment.EnchantmentsSource.HELD;

/**
 * @author Ameliorate
 */
public class ItemEnchantmentCountMatcher implements ItemMatcher {
	public ItemEnchantmentCountMatcher(AmountMatcher enchantmentCount, EnchantmentsSource source) {
		this.enchantmentCount = enchantmentCount;
		this.source = source;
	}

	public ItemEnchantmentCountMatcher(AmountMatcher enchantmentCount) {
		this(enchantmentCount, EnchantmentsSource.ITEM);
	}

	public AmountMatcher enchantmentCount;
	public EnchantmentsSource source;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta())
			return false;
		if (source == HELD && !(item.getItemMeta() instanceof EnchantmentStorageMeta))
			return false;

		int count = 0;
		switch (source) {
			case HELD:
				count = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants().size();
				break;
			case ITEM:
				count = item.getItemMeta().getEnchants().size();
				break;
		}

		return enchantmentCount.matches(count);
	}

	private static List<Enchantment> allEnchantments = new ArrayList<>();

	static {
		Class<Enchantment> enchantmentClass = Enchantment.class;
		List<Field> staticFields = Arrays.stream(enchantmentClass.getFields())
				.filter((f) -> Modifier.isStatic(f.getModifiers())) // is static
				.filter((f) -> f.getType().isAssignableFrom(Enchantment.class)) // is of type Enchantment
				.filter((f) -> Modifier.isPublic(f.getModifiers())) // is public
				.collect(Collectors.toList()); // in other words, get all the enchantments declared in Enchangments.

		staticFields.forEach((f) -> {
			try {
				allEnchantments.add((Enchantment) f.get(Enchantment.PROTECTION_FALL));
			} catch (IllegalAccessException e) {
				throw new AssertionError("expected f to be a public member", e);
			}
		});
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		int i = 0;
		boolean unsafe = false;

		while (!enchantmentCount.matches(source.get(item).size())) {
			if (unsafe || source == HELD || allEnchantments.get(i).canEnchantItem(item))
				source.add(item, allEnchantments.get(i), 1, unsafe);
			i++;

			if (i >= allEnchantments.size() && unsafe)
				throw new NotSolvableException("not enough enchantments exist to solve for enchantment count on item");

			if (i >= allEnchantments.size()) {
				// if can't get enough enchantments with safe enchantments, add some unsafe enchantments.
				unsafe = true;
				i = 0;
			}
		}

		return item;
	}
}
