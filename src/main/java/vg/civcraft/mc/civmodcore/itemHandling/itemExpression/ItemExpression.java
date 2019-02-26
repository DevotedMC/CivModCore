package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.SkullMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.ItemUnbreakableMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.ItemSkullMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment.EnchantmentsSource.HELD;
import static vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment.EnchantmentsSource.ITEM;
import static vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment.ItemEnchantmentsMatcher.Mode.*;

/**
 * A unified syntax for matching any ItemStack for things like the material, amount, lore contents, and more.
 *
 * While mostly designed to be used in a .yaml config file, this can also be used from java.
 *
 * @author Ameliorate
 */
public class ItemExpression {
	/**
	 * Creates the default ItemExpression.
	 *
	 * By default, it will match any ItemStack.
	 */
	public ItemExpression() {}

	/**
	 * Creates an ItemExpression from a section of bukkit configuration format.
	 * @param configurationSection The subsection of config that should be parsed.
	 */
	public ItemExpression(ConfigurationSection configurationSection) {
		parseConfig(configurationSection);
	}

	/**
	 * Creates an ItemExpression that matches exactly the passed ItemStack, and no other item.
	 *
	 * Note that because of how ItemExpression is implemented, if ItemExpression does not support matching an element
	 * of an item, this will accept any item with that element. For example, if ItemExpression did not support
	 * matching the player on a player skull (it supports it), this constructor would return an ItemExpression
	 * that matched any player head even when passed a player head with a specific name.
	 * @param item The ItemStack that this ItemExpression would exactly match.
	 */
	public ItemExpression(ItemStack item) {
		this(item, false);
	}

	/**
	 * Creates an ItemExpression that matches exactly the passed ItemStack, or acts equilivent to ItemStack.isSimilar().
	 *
	 * See also ItemExpression(ItemStack).
	 * @param item The item that this ItemExpression would match.
	 * @param acceptSimilar If this ItemExpression should act similar to ItemStack.isSimilar().
	 */
	public ItemExpression(ItemStack item, boolean acceptSimilar) {
		// material
		addMatcher(new ItemMaterialMatcher(new ExactlyMaterial(item.getType())));

		// amount
		if (!acceptSimilar)
			addMatcher(new ItemAmountMatcher(new ExactlyAmount(item.getAmount())));

		// stop here if there isn't any itemmeta, so not every other matcher needs to check
		if (!item.hasItemMeta())
			return;

		// durability
		if (item.getItemMeta() instanceof Damageable)
			addMatcher(new ItemDurabilityMatcher(new ExactlyAmount(((Damageable) item.getItemMeta()).getDamage())));

		// lore
		addMatcher(new ItemLoreMatcher(new ExactlyLore(item.getItemMeta().hasLore() ?
				item.getItemMeta().getLore() : Collections.emptyList())));

		// name
		if (item.getItemMeta().hasDisplayName())
			addMatcher(new ItemNameMatcher(new ExactlyName(item.getItemMeta().getDisplayName())));
		else
			addMatcher(new ItemNameMatcher(new VanillaName()));

		// enchantments
		addMatcher(new ItemExactlyEnchantmentsMatcher(item.getEnchantments(), ITEM));

		// enchantments held like an enchanted book
		if (item.getItemMeta() instanceof EnchantmentStorageMeta)
			addMatcher(new ItemExactlyEnchantmentsMatcher(((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants(), HELD));
		else
			addMatcher(new ItemZeroEnchantsMatcher(HELD));

		// skulls
		if (item.getItemMeta() instanceof SkullMeta && ((SkullMeta) item.getItemMeta()).hasOwner())
			addMatcher(new ItemSkullMatcher(Collections.singletonList(new ExactlyUUID(((SkullMeta) item.getItemMeta()).getOwningPlayer().getUniqueId()))));
		else if (item.getItemMeta() instanceof SkullMeta && !((SkullMeta) item.getItemMeta()).hasOwner())
			addMatcher(new ItemSkullMatcher(Collections.singletonList(new ExactlyUUID(new UUID(0, 0)))));

		// unbreakable
		addMatcher(new ItemUnbreakableMatcher(item.getItemMeta().isUnbreakable()));
	}

	/**
	 * Mutate this ItemExpression, overriding the existing options set for this with the options given in the
	 * ConfigurationSection.
	 * @param config The config that options will be taken from.
	 */
	public void parseConfig(ConfigurationSection config) {
		addMatcher(new ItemMaterialMatcher(parseMaterial(config, "material")));
		addMatcher(new ItemAmountMatcher(parseAmount(config, "amound")));
		addMatcher(new ItemDurabilityMatcher(parseAmount(config, "durability")));
		addMatcher(new ItemLoreMatcher(parseLore(config, "lore")));
		addMatcher(new ItemNameMatcher(parseName(config, "name")));
		addMatcher(parseEnchantment(config, "enchantmentsAny", ANY, ITEM));
		addMatcher(parseEnchantment(config, "enchantmentsAll", ALL, ITEM));
		addMatcher(parseEnchantment(config, "enchantmentsNone", NONE, ITEM));
		addMatcher(parseEnchantment(config, "enchantmentsHeldAny", ANY, HELD));
		addMatcher(parseEnchantment(config, "enchantmentsHeldAll", ALL, HELD));
		addMatcher(parseEnchantment(config, "enchantmentsHeldNone", NONE, HELD));
		addMatcher(new ItemSkullMatcher(parseSkull(config, "skull")));
	}

	/**
	 * Gets a ItemExpression from the given path in the config
	 * @param configurationSection The config to get the ItemExpression from
	 * @param path The path to the ItemExpression
	 * @return The ItemExpression in the config that path points to, or null if there was not an ItemExpression at path.
	 */
	public static ItemExpression getItemExpression(ConfigurationSection configurationSection, String path) {
		if (configurationSection == null)
			return null;
		if (!configurationSection.contains(path))
			return null;
		return new ItemExpression(configurationSection.getConfigurationSection(path));
	}

	private MaterialMatcher parseMaterial(ConfigurationSection config, String path) {
		if (config.contains(path + ".regex"))
			return(new RegexMaterial(Pattern.compile(config.getString(path + ".regex"))));
		else if (config.contains(path))
			return(new ExactlyMaterial(Material.getMaterial(config.getString(path))));
		return null;
	}

	private AmountMatcher parseAmount(ConfigurationSection config, String path) {
		if (config.contains(path + ".range"))
			return(new RangeAmount(
					config.getInt(path + ".range.low", 0),
					config.getInt(path + ".range.high"),
					config.getBoolean(path + ".range.inclusiveLow", true),
					config.getBoolean(path + ".range.inclusiveHigh", true)));
		else if ("any".equals(config.getString(path)))
			return(new AnyAmount());
		else if (config.contains(path))
			return(new ExactlyAmount(config.getInt(path)));
		return null;
	}

	private LoreMatcher parseLore(ConfigurationSection config, String path) {
		if (config.contains(path + ".regex")) {
			String patternStr = config.getString(path + ".regex");
			boolean multiline = config.getBoolean(path + ".regexMultiline", true);
			Pattern pattern = Pattern.compile(patternStr, multiline ? Pattern.MULTILINE : 0);

			return(new RegexLore(pattern));
		} else if (config.contains(path))
			return(new ExactlyLore(config.getStringList(path)));
		return null;
	}

	private NameMatcher parseName(ConfigurationSection config, String path) {
		if (config.contains(path + ".regex"))
			return(new RegexName(Pattern.compile(config.getString(path + ".regex"))));
		else if ("vanilla".equals(config.getString(path)))
			return(new VanillaName());
		else if (config.contains(path))
			return(new ExactlyName(config.getString(path)));
		return null;
	}

	private ItemEnchantmentsMatcher parseEnchantment(ConfigurationSection config, String path,
													 ItemEnchantmentsMatcher.Mode mode,
													 EnchantmentsSource source) {
		ConfigurationSection enchantments = config.getConfigurationSection(path);
		if (enchantments == null)
			return null;

		ArrayList<EnchantmentMatcher> enchantmentMatcher = new ArrayList<>();
		for (String enchantName : enchantments.getKeys(false)) {
			enchantmentMatcher.add(
					new ExactlyEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(enchantName.toLowerCase())),
							parseAmount(config, path + "." + enchantName)));
		}

		return new ItemEnchantmentsMatcher(enchantmentMatcher, mode, source);
	}

	private List<UUIDMatcher> parseSkull(ConfigurationSection config, String path) {
		List<UUIDMatcher> matchers = new ArrayList<>();
		ConfigurationSection skull = config.getConfigurationSection(path);
		if (skull == null)
			return Collections.emptyList();

		for (String name : skull.getStringList("names")) {
			matchers.add(new PlayerNameUUID(name));
		}

		for (String uuid : skull.getStringList("uuids")) {
			matchers.add(new ExactlyUUID(UUID.fromString(uuid)));
		}

		if (skull.contains("name"))
			matchers.add(new PlayerNameUUID(skull.getString("name")));
		if (skull.contains("uuid"))
			matchers.add(new ExactlyUUID(UUID.fromString(skull.getString("name"))));

		return matchers;
	}

	/**
	 * Runs this ItemExpression on a given ItemStack.
	 *
	 * This will not mutate the ItemStack nor this ItemExpression.
	 * @param item The ItemStack to be matched upon.
	 * @return If the given item matches.
	 */
	public boolean matches(ItemStack item) {
		return matchers.stream().allMatch((matcher) -> matcher.matches(item));
	}

	/**
	 * Removes amount items that match this ItemExpression from tne inventory.
	 *
	 * This function correctly handles situations where the inventory has two or more ItemStacks that do not satisfy
	 * .isSimilar() but do match this ItemExpression.
	 * @param inventory The inventory items will be removed from.
	 * @param amount The number of items to remove. If this is -1, all items that match will be removed.
	 * @return If there were enough items to remove. If this is false, no items have been removed from the inventory.
	 */
	public boolean removeFromInventory(Inventory inventory, int amount) {
		// store the amount matchers, because it'll mess with things later
		// for exacple, what happens when amount=1 was passed into this function but amount: 64 is in the config?
		List<ItemMatcher> amountMatchers = matchers.stream().filter((m) -> m instanceof ItemAmountMatcher).collect(Collectors.toList());
		for (ItemMatcher m : amountMatchers) {
			matchers.remove(m);
		}

		try {
			int runningAmount = amount;
			boolean infinite = false;
			if (runningAmount == -1) {
				runningAmount = Integer.MAX_VALUE;
				infinite = true;
			}

			ItemStack[] contents = inventory.getStorageContents();
			contents = Arrays.stream(contents).map(item -> item != null ? item.clone() : null).toArray(ItemStack[]::new);
			for (ItemStack item : contents) {
				if (item == null)
					continue;
				if (item.getType() == Material.AIR)
					continue;

				if (matches(item)) {
					if (item.getAmount() >= runningAmount) {
						int itemOldAmount = item.getAmount();
						item.setAmount(item.getAmount() - runningAmount);
						runningAmount -= itemOldAmount - item.getAmount();
						break;
					} else if (item.getAmount() < runningAmount) {
						runningAmount -= item.getAmount();
						item.setAmount(0);
					}
				}
			}

			if (runningAmount == 0 || infinite) {
				inventory.setStorageContents(contents);
				return true;
			} else if (runningAmount < 0) {
				// big trouble, this isn't supposed to happen
				throw new AssertionError("runningAmount is less than 0, there's a bug somewhere. runningAmount: " + runningAmount);
			} else {
				// items remaining
				return false;
			}
		} finally {
			// restore the amount matchers now we're done
			amountMatchers.forEach(this::addMatcher);
		}
	}

	/**
	 * Removes the items that match this ItemExpression. The amount to remove is infered from the amount of this
	 * ItemExpression.
	 *
	 * If amount is `any`, all items that match this ItemExpression will be removed.
	 * If amount is a range and random is true, a random number of items within the range will be removed.
	 * If amount is a range and random is false, the lower bound of the range will be used.
	 * @param inventory The inventory items will be removed from.
	 * @param random To select a random number within amount. This only applies if amount is a range.
	 * @return If there were enough items to remove. If this is false, no items have been removed from the inventory.
	 */
	public boolean removeFromInventory(Inventory inventory, boolean random) {
		int amount;
		List<ItemAmountMatcher> amountMatchers = matchers.stream()
				.filter((m) -> m instanceof ItemAmountMatcher)
				.map((m) -> (ItemAmountMatcher) m)
				.collect(Collectors.toList());

		AmountMatcher amountMatcher;
		if (amountMatchers.size() > 1)
			throw new IllegalStateException("Can't infer the amount from an ItemExpression with multiple " +
					"ItemAmountMatchers.");
		else if (amountMatchers.size() == 1)
			amountMatcher = amountMatchers.get(0).matcher;
		else {
			amountMatcher = new AnyAmount();
		}

		if (amountMatcher instanceof ExactlyAmount) {
			amount = ((ExactlyAmount) amountMatcher).amount;
		} else if (amountMatcher instanceof AnyAmount) {
			amount = -1;
		} else if (amountMatcher instanceof RangeAmount && !random) {
			RangeAmount rangeAmount = (RangeAmount) amountMatcher;
			amount = rangeAmount.getLow() + (rangeAmount.lowInclusive ? 0 : 1);
		} else if (amountMatcher instanceof RangeAmount && random) {
			RangeAmount rangeAmount = (RangeAmount) amountMatcher;
			amount = ThreadLocalRandom.current()
					.nextInt(rangeAmount.getLow() + (rangeAmount.lowInclusive ? 0 : -1),
							rangeAmount.getHigh() + (rangeAmount.highInclusive ? 1 : 0));
		} else {
			throw new IllegalArgumentException("removeFromInventory(Inventory, boolean) does not work with custom AmountMatchers");
		}

		return removeFromInventory(inventory, amount);
	}

	/**
	 * Add a property of the item to be checked, using an ItemMatcher.
	 * @param matcher The ItemMatcher to be added to the list that will be checked aganst each item inside
	 *                ItemExpression.matches(ItemStack). If this is null, this function will do nothing.
	 */
	public void addMatcher(ItemMatcher matcher) {
		if (matcher != null)
			matchers.add(matcher);
	}

	/**
	 * All of the matchers in this set must return true in order for this ItemExpression to match a given item.
	 *
	 * This is the only data structure holding ItemMatchers in this ItemExpression, so it is fine to mutate this field.
	 */
	public HashSet<ItemMatcher> matchers = new HashSet<>();
}
