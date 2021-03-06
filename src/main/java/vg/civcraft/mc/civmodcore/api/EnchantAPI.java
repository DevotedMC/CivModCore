package vg.civcraft.mc.civmodcore.api;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.inventory.items.EnchantUtils;

/**
 * Class of static APIs for Enchantments.
 *
 * @deprecated Use {@link EnchantUtils} instead.
 */
@Deprecated
public final class EnchantAPI {

	/**
	 * Determines whether an enchantment is considered safe.
	 *
	 * @param enchantment The enchantment to validate.
	 * @param level The enchantment level to validate.
	 * @return Returns true if the enchantment is not null, and the level is within the acceptable bounds.
	 *
	 * @see Enchantment#getStartLevel() The starting level. A valid level cannot be below this.
	 * @see Enchantment#getMaxLevel() The maximum level. A valid level cannot be above this.
	 *
	 * @deprecated Use {@link EnchantUtils#isSafeEnchantment(Enchantment, int)} instead.
	 */
	@Deprecated
	public static boolean isSafeEnchantment(Enchantment enchantment, int level) {
		return enchantment != null && level >= enchantment.getStartLevel() && level <= enchantment.getMaxLevel();
	}

	/**
	 * Attempts to retrieve an enchantment by its slug, display name, and abbreviation.
	 *
	 * @param value The value to search for a matching enchantment by.
	 * @return Returns a matched enchantment or null.
	 *
	 * @deprecated Use {@link EnchantUtils#getEnchantment(String)} instead.
	 */
	@Deprecated
	@SuppressWarnings("deprecation")
	public static Enchantment getEnchantment(String value) {
		if (Strings.isNullOrEmpty(value)) {
			return null;
		}
		Enchantment enchantment = Enchantment.getByKey(NamespaceAPI.fromString(value));
		if (enchantment != null) {
			return enchantment;
		}
		enchantment = Enchantment.getByName(value.toUpperCase());
		if (enchantment != null) {
			return enchantment;
		}
		EnchantNames.SearchResult search = EnchantNames.findByDisplayName(value);
		if (search != null) {
			return search.getEnchantment();
		}
		search = EnchantNames.findByAbbreviation(value);
		if (search != null) {
			return search.getEnchantment();
		}
		return null;
	}

	/**
	 * Gets the enchantments from an item.
	 *
	 * @param item The item to retrieve the enchantments from.
	 * @return Returns the item's enchantments, which are never null.
	 *
	 * @deprecated Use {@link EnchantUtils#getEnchantments(ItemStack)} instead.
	 */
	@Deprecated
	public static Map<Enchantment, Integer> getEnchantments(ItemStack item) {
		Preconditions.checkArgument(ItemAPI.isValidItem(item));
		return item.getEnchantments();
	}

	/**
	 * Adds a safe enchantment to an item.
	 *
	 * @param item The item to add the enchantment to.
	 * @param enchantment The enchantment to add to the item.
	 * @param level The level of the enchantment to add to the item.
	 * @return Returns true if the enchantment was successfully added.
	 *
	 * @see EnchantAPI#isSafeEnchantment(Enchantment, int)
	 *
	 * @deprecated Use {@link EnchantUtils#addEnchantment(ItemStack, Enchantment, int)} instead.
	 */
	@Deprecated
	public static boolean addEnchantment(ItemStack item, Enchantment enchantment, int level) {
		return addEnchantment(item, enchantment, level, true);
	}

	/**
	 * Adds an enchantment to an item.
	 *
	 * @param item The item to add the enchantment to.
	 * @param enchantment The enchantment to add to the item.
	 * @param level The level of the enchantment to add to the item.
	 * @param onlyAllowSafeEnchantments Requires enchantments to be safe if set to true.
	 * @return Returns true if the enchantment was successfully added.
	 *
	 * @see EnchantAPI#isSafeEnchantment(Enchantment, int)
	 *
	 * @deprecated Use {@link EnchantUtils#addEnchantment(ItemStack, Enchantment, int, boolean)} instead.
	 */
	@Deprecated
	public static boolean addEnchantment(ItemStack item, Enchantment enchantment, int level,
		 	boolean onlyAllowSafeEnchantments) {
		Preconditions.checkArgument(ItemAPI.isValidItem(item));
		return ItemAPI.handleItemMeta(item, (ItemMeta meta) ->
				meta.addEnchant(enchantment, level, !onlyAllowSafeEnchantments));
	}

	/**
	 * Removes an enchantment from an item.
	 *
	 * @param item The item to remove the enchantment from.
	 * @param enchant The enchantment to remove from the item.
	 * @return Returns true if the enchantment was successfully removed.
	 *
	 * @deprecated Use {@link EnchantUtils#removeEnchantment(ItemStack, Enchantment)} instead.
	 */
	@Deprecated
	public static boolean removeEnchantment(ItemStack item, Enchantment enchant) {
		Preconditions.checkArgument(ItemAPI.isValidItem(item));
		if (enchant == null) {
			return true;
		}
		return ItemAPI.handleItemMeta(item, (ItemMeta meta) -> meta.removeEnchant(enchant));
	}

	/**
	 * Removes all enchantments from an item.
	 *
	 * @param item The item to clear enchantment from.
	 *
	 * @deprecated Use {@link EnchantUtils#clearEnchantments(ItemStack)} instead.
	 */
	@Deprecated
	public static void clearEnchantments(ItemStack item) {
		Preconditions.checkArgument(ItemAPI.isValidItem(item));
		ItemAPI.handleItemMeta(item, (ItemMeta meta) -> {
			meta.getEnchants().forEach((key, value) -> meta.removeEnchant(key));
			return true;
		});
	}

}
