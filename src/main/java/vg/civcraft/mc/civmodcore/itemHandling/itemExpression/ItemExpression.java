package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.*;
import vg.civcraft.mc.civmodcore.itemHandling.ItemMap;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.book.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.ItemExactlyInventoryMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.*;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
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
	 * This constructor uses ItemStack.equals() directly, so this supports all aspects of an item, even those that are
	 * not supported by ItemExpression.
	 *
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
	 * @param acceptSimilar If this ItemExpression should use ItemStack.isSimilar() instead of .equals().
	 */
	public ItemExpression(ItemStack item, boolean acceptSimilar) {
		addMatcher(new ItemExactlyStackMatcher(item, acceptSimilar));
	}

	/**
	 * Mutate this ItemExpression, overriding the existing options set for this with the options given in the
	 * ConfigurationSection.
	 * @param config The config that options will be taken from.
	 */
	public void parseConfig(ConfigurationSection config) {
		addMatcher(new ItemMaterialMatcher(parseMaterial(config, "material")));
		addMatcher(new ItemAmountMatcher(parseAmount(config, "amount")));
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
		parseFlags(config, "flags").forEach(this::addMatcher);
		addMatcher(parseUnbreakable(config, "unbreakable"));
		addMatcher(parseInventory(config, "inventory"));
		parseBook(config, "book").forEach(this::addMatcher);
		addMatcher(parseExactly(config, "exactly"));
		addMatcher(parseShulkerBoxColor(config, "shulkerbox.color", false));
		addMatcher(parseShulkerBoxColor(config, "shulkerbox.colorAny", false));
		addMatcher(parseShulkerBoxColor(config, "shulkerbox.colorNone", true));
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

	public static List<ItemExpression> getItemExpressionList(ConfigurationSection config, String path) {
		if (!config.contains(path))
			return Collections.emptyList();

		List<ConfigurationSection> itemExpressionsConfig = getConfigList(config, path);
		List<ItemExpression> itemExpressions = new ArrayList<>();

		for (ConfigurationSection itemExConfig : itemExpressionsConfig) {
			itemExpressions.add(new ItemExpression(itemExConfig));
		}

		return itemExpressions;
	}

	@SuppressWarnings("unchecked") // fix your warnings, java
	private static List<ConfigurationSection> getConfigList(ConfigurationSection config, String path)
	{
		if (!config.isList(path))
			return Collections.emptyList();

		List<ConfigurationSection> list = new ArrayList<>();

		for (Object object : config.getList(path)) {
			if (object instanceof Map) {
				MemoryConfiguration mc = new MemoryConfiguration();

				mc.addDefaults((Map<String, Object>) object);

				list.add(mc);
			}
		}

		return list;
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
			EnchantmentMatcher matcher;
			AmountMatcher amountMatcher = parseAmount(config, path + "." + enchantName);
			if (enchantName.equals("any")) {
				matcher = new AnyEnchantment(amountMatcher);
			} else {
				matcher = new AnyEnchantment(amountMatcher);
			}

			enchantmentMatcher.add(matcher);
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

		if (skull.contains("regex"))
			matchers.add(new PlayerNameRegexUUID(Pattern.compile(skull.getString("regex"))));

		return matchers;
	}

	private List<ItemFlagMatcher> parseFlags(ConfigurationSection config, String path) {
		List<ItemFlagMatcher> matchers = new ArrayList<>();

		ConfigurationSection flags = config.getConfigurationSection(path);
		if (flags == null)
			return Collections.emptyList();

		for (String flagKey : flags.getKeys(false)) {
			ItemFlag flag = ItemFlag.valueOf(flagKey.toUpperCase());
			boolean setting = flags.getBoolean(flagKey);

			matchers.add(new ItemFlagMatcher(flag, setting));
		}

		return matchers;
	}

	private ItemUnbreakableMatcher parseUnbreakable(ConfigurationSection config, String path) {
		if (!config.contains(path))
			return null;
		boolean unbreakable = config.getBoolean(path);
		return new ItemUnbreakableMatcher(unbreakable);
	}

	private ItemExactlyInventoryMatcher parseInventory(ConfigurationSection config, String path) {
		List<ItemExpression> itemExpressions = getItemExpressionList(config, path);
		if (itemExpressions.isEmpty())
			return null;

		return new ItemExactlyInventoryMatcher(itemExpressions);
	}

	private List<ItemMatcher> parseBook(ConfigurationSection config, String path) {
		if (!config.contains(path))
			return Collections.emptyList();

		ConfigurationSection book = config.getConfigurationSection(path);
		ArrayList<ItemMatcher> matchers = new ArrayList<>();

		// author
		if (book.contains("author")) {
			matchers.add(new ItemBookAuthorMatcher(parseName(book, "author")));
		}

		// generation
		ArrayList<BookMeta.Generation> generations = new ArrayList<>();

		if (book.contains("generation") && !book.isList("generation")) {
			BookMeta.Generation generation = BookMeta.Generation.valueOf(book.getString("generation").toUpperCase());
			generations.add(generation);
		} else if (book.isList("generation")) {
			for (String generationS : book.getStringList("generation")) {
				BookMeta.Generation generation = BookMeta.Generation.valueOf(generationS.toUpperCase());
				generations.add(generation);
			}
		}

		matchers.add(new ItemBookGenerationMatcher(generations));

		// title
		if (book.contains("title")) {
			matchers.add(new ItemBookTitleMatcher(parseName(book, "title")));
		}

		// pages
		if (book.contains("pages.regex")) {
			boolean isMultiline = book.getBoolean("pages.regexMultiline", true);
			Pattern pattern = Pattern.compile(book.getString("pages.regex"),isMultiline ? Pattern.MULTILINE : 0);

			matchers.add(new ItemBookPagesMatcher(new RegexBookPages(pattern)));
		} else if (book.isList("pages")) {
			List<String> pages = book.getStringList("pages");
			matchers.add(new ItemBookPagesMatcher(new ExactlyBookPages(pages)));
		}

		return matchers;
	}

	private ItemExactlyStackMatcher parseExactly(ConfigurationSection config, String path) {
		if (!config.contains(path))
			return null;

		boolean acceptBoolean = config.getBoolean(path + ".acceptSimilar");

		return new ItemExactlyStackMatcher(config.getItemStack(path), acceptBoolean);
	}

	private ItemShulkerBoxColorMatcher parseShulkerBoxColor(ConfigurationSection config, String path, boolean notInList) {
		if (!config.contains(path))
			return null;

		if (config.isList(path)) {
			ArrayList<DyeColor> colors = new ArrayList<>();
			config.getStringList(path).stream().map((c) -> DyeColor.valueOf(c.toUpperCase())).forEach(colors::add);
			return new ItemShulkerBoxColorMatcher(colors, notInList);
		} else {
			return new ItemShulkerBoxColorMatcher(
					Collections.singletonList(DyeColor.valueOf(config.getString(path).toUpperCase())), notInList);
		}
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
	 * Returns a lambda with the ItemMap bound into its environment. This is an instance of currying in java.
	 *
	 * The lambda returned uses the Entry it is passed to match on the ItemStack key. If the matcher implements
	 * ItemMapMatcher, it'll use that interface instead of the normal ItemMatcher. The reason it used an Entry instead
	 * of directly the ItemStack contained within is that it's designed to be used to be used as
	 * ItemMap.getEntries().stream().anyMatch(getMatchesItemMapPredicate(ItemMap)).
	 *
	 * If you wanted to call this function directly you'd say `getMatchesItemMapPredicate(itemMap)(entry)`.
	 *
	 * This function is implemented in this way in order to be able to reuse the predicate inside multiple functions
	 * while still being able to have the ItemMap usable inside the lambda.
	 *
	 * This function is mostly used to implement ItemMap advanced matching. It is not recommended to be used.
	 * @param itemMap The curried ItemMap value
	 * @return The curried function.
	 */
	public Predicate<Map.Entry<ItemStack, Integer>> getMatchesItemMapPredicate(ItemMap itemMap) {
		// currying in java 2019
		return (kv) -> {
			ItemStack item = kv.getKey();
			//Integer amount = kv.getValue();
			return matchers.stream().allMatch((matcher) -> {
				if (matcher instanceof ItemMapMatcher) {
					return ((ItemMapMatcher) matcher).matches(itemMap, item);
				} else {
					return matcher.matches(item);
				}
			});
		};
	}

	/**
	 * Runs this ItemExpression on a given ItemMap, and returns true if the ItemExpression matched any one of the
	 * ItemStacks contained within the ItemMap.
	 * @param itemMap The ItemMap this ItemExpression will match over.
	 * @return If this ItemExpression matched at least one of the ItemStacks within the ItemMap.
	 */
	public boolean matchesAnyItemMap(ItemMap itemMap) {
		return itemMap.getEntrySet().stream().anyMatch(getMatchesItemMapPredicate(itemMap));
	}

	/**
	 * Runs this ItemExpression on a given ItemMap, and returns true if the ItemExpression matched all of the
	 * ItemStacks contained within the ItemMap.
	 * @param itemMap The ItemMap this ItemExpression will match over.
	 * @return If this ItemExpression matched every one of the ItemStacks within the ItemMap.
	 */
	public boolean matchesAllItemMap(ItemMap itemMap) {
		return itemMap.getEntrySet().stream().allMatch(getMatchesItemMapPredicate(itemMap));
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
		ItemStack[] contents = inventory.getStorageContents();

		ItemStack[] result = removeFromItemArray(contents, amount);
		if (result == null)
			return false;

		inventory.setStorageContents(result);
		return true;
	}

	/**
	 * Removes amount items that match this ItemExpression from contents, returning the modified version of contents.
	 * @param contents The list of items to be matched and possibly removed. This may contain nulls.
	 *                 The array and the ItemStacks inside will not be mutated.
	 * @param amount The number of items to remove.
	 * @return The new list of items will amount removed. If there were not enough items to remove, null will be returned.
	 */
	private ItemStack[] removeFromItemArray(ItemStack[] contents, int amount) {
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
				return contents;
			} else if (runningAmount < 0) {
				// big trouble, this isn't supposed to happen
				throw new AssertionError("runningAmount is less than 0, there's a bug somewhere. runningAmount: " + runningAmount);
			} else {
				// items remaining
				return null;
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
		return removeFromInventory(inventory, getAmount(random));
	}

	/**
	 * Abstraction for the algorithm defined in removeFromInventory's javadoc.
	 * @param random To select a random number within amount. This only applies if amount is a range.
	 * @return The amount field of the config format. This is extracted from the structure of this ItemStack, not the config.
	 */
	private int getAmount(boolean random) {
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
			return ((ExactlyAmount) amountMatcher).amount;
		} else if (amountMatcher instanceof AnyAmount) {
			return -1;
		} else if (amountMatcher instanceof RangeAmount && !random) {
			RangeAmount rangeAmount = (RangeAmount) amountMatcher;
			return rangeAmount.getLow() + (rangeAmount.lowInclusive ? 0 : 1);
		} else if (amountMatcher instanceof RangeAmount && random) {
			RangeAmount rangeAmount = (RangeAmount) amountMatcher;
			return ThreadLocalRandom.current()
					.nextInt(rangeAmount.getLow() + (rangeAmount.lowInclusive ? 0 : -1),
							rangeAmount.getHigh() + (rangeAmount.highInclusive ? 1 : 0));
		} else {
			throw new IllegalArgumentException("removeFromInventory(Inventory, boolean) does not work with custom AmountMatchers");
		}
	}

	/**
	 * Removes amount items that match this ItemExpression from the main hand or the off hand, prefeing the main hand.
	 * @param inventory The player's inventory to remove the items from.
	 * @param amount The number of items to remove. All ItemStacks that match will be removed if this is -1.
	 * @return If there were enough items to remove. If this is false, no items were removed.
	 */
	public boolean removeFromMainHandOrOffHand(PlayerInventory inventory, int amount) {
		ItemStack[] items = new ItemStack[2];
		items[0] = inventory.getItemInMainHand();
		items[1] = inventory.getItemInOffHand();

		ItemStack[] result = removeFromItemArray(items, amount);
		if (result == null)
			return false;

		inventory.setItemInMainHand(result[0]);
		inventory.setItemInOffHand(result[1]);
		return true;
	}

	/**
	 * Removes the items that match this ItemExpression from either the main hand or the oof hand of the player.
	 * The amount to remove is infered from the amount of this ItemExpression.
	 *
	 * If amount is `any`, all items that match this ItemExpression will be removed.
	 * If amount is a range and random is true, a random number of items within the range will be removed.
	 * If amount is a range and random is false, the lower bound of the range will be used.
	 * @param inventory The player inventory to remove the items from.
	 * @param random To select a random number within amount. This only applies if amount is a range.
	 * @return If there were enough items to remove. If this is false, no items have been removed from the inventory.
	 */
	public boolean removeFromMainHandOrOffHand(PlayerInventory inventory, boolean random) {
		return removeFromMainHandOrOffHand(inventory, getAmount(random));
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
