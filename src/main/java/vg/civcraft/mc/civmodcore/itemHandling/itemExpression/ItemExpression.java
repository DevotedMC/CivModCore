package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AnyAmount;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.ExactlyAmount;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.RangeAmount;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment.AnyEnchantment;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment.EnchantmentMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment.ExactlyEnchantment;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore.AnyLore;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore.ExactlyLore;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore.LoreMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore.RegexLore;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.AnyMaterial;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.ExactlyMaterial;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.MaterialMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.RegexMaterial;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.*;

import java.util.*;
import java.util.regex.Pattern;

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
	 * Mutate this ItemExpression, overriding the existing options set for this with the options given in the
	 * ConfigurationSection.
	 * @param config The config that options will be taken from.
	 */
	public void parseConfig(ConfigurationSection config) {
		setMaterial(parseMaterial(config, "material"));
		setAmount(parseAmount(config, "amount"));
		setDurability(parseAmount(config, "durability"));
		setLore(parseLore(config, "lore"));
		setName(parseName(config, "name"));
		setEnchantmentAny(parseEnchantment(config, "enchantmentsAny"));
		setEnchantmentAll(parseEnchantment(config, "enchantmentsAll"));
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
		else if ("any".equals(config.getString(path)))
			// yoda order because config.getString is null if doesn't exist
			return(new AnyMaterial());
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
			List<String> patternsStr = config.getStringList(path + ".regex");
			List<Pattern> patterns = new ArrayList<>();
			boolean multiline = config.getBoolean(path + ".regexMultiline", true);

			for (String patternStr : patternsStr) {
				patterns.add(Pattern.compile(patternStr, multiline ? Pattern.MULTILINE : 0));
			}
			return(new RegexLore(patterns));
		} else if ("any".equals(config.getString(path)))
			return(new AnyLore());
		else if (config.contains(path))
			return(new ExactlyLore(config.getStringList(path)));
		return null;
	}

	private NameMatcher parseName(ConfigurationSection config, String path) {
		if (config.contains(path + ".regex"))
			return(new RegexName(Pattern.compile(config.getString(path + ".regex"))));
		else if ("any".equals(config.getString(path)))
			return(new AnyName());
		else if ("vanilla".equals(config.getString(path)))
			return(new VanillaName());
		else if (config.contains(path))
			return(new ExactlyName(config.getString(path)));
		return null;
	}

	private EnchantmentSetMatcher parseEnchantment(ConfigurationSection config, String path) {
		ConfigurationSection enchantments = config.getConfigurationSection(path);
		if (enchantments == null)
			return null;

		ArrayList<EnchantmentMatcher> enchantmentMatcher = new ArrayList<>();
		for (String enchantName : enchantments.getKeys(false)) {
			if (enchantName.equals("mode"))
				continue;

			if (config.getString(path + "." + enchantName).equals("any"))
				// don't think too much about enchantmentsAll: any
				// it works out
				enchantmentMatcher.add(new AnyEnchantment());
			else
				enchantmentMatcher.add(
						new ExactlyEnchantment(Enchantment.getByName(enchantName),
						    parseAmount(config, path + "." + enchantName)));
		}

		return new EnchantmentSetMatcher(enchantmentMatcher);
	}

	/**
	 * Runs this ItemExpression on a given ItemStack.
	 *
	 * This will not mutate the ItemStack nor this ItemExpression.
	 * @param item The ItemStack to be matched upon.
	 * @return If the given item matches.
	 */
	public boolean matches(ItemStack item) {
		if (!materialMatcher.matches(item.getType()))
			return false;
		else if (!amountMatcher.matches(item.getAmount()))
			return false;
		else if (!durabilityMatcher.matches(item.getDurability()))
			return false;
		else if (!item.hasItemMeta() && !(loreMatcher instanceof AnyLore) && !(nameMatcher instanceof AnyName))
			// slightly gross, but passing in null if !hasItemMeta is also kinda gross
			// the code here wouldn't look nice either
			// java null chaining operator when?.
			return false;
		else if (!loreMatcher.matches(item.getItemMeta().getLore()))
			return false;
		else if (!nameMatcher.matches(item.getItemMeta().getDisplayName()))
			return false;
		else if (!enchantmentMatcherAny.matches(item.getEnchantments(), true))
			return false;
		else if (!enchantmentMatcherAll.matches(item.getEnchantments(), false))
			return false;
		return true;
	}

	private MaterialMatcher materialMatcher = new AnyMaterial();

	public MaterialMatcher getMaterial() {
		return materialMatcher;
	}

	public void setMaterial(MaterialMatcher materialMatcher) {
		if (materialMatcher == null)
			return;
		this.materialMatcher = materialMatcher;
	}

	private AmountMatcher amountMatcher = new AnyAmount();

	public AmountMatcher getAmount() {
		return amountMatcher;
	}

	public void setAmount(AmountMatcher amountMatcher) {
		if (amountMatcher == null)
            return;
		this.amountMatcher = amountMatcher;
	}

	private LoreMatcher loreMatcher = new AnyLore();

	public LoreMatcher getLore() {
		return loreMatcher;
	}

	public void setLore(LoreMatcher loreMatcher) {
		if (loreMatcher == null)
            return;
		this.loreMatcher = loreMatcher;
	}

	private NameMatcher nameMatcher;

	public NameMatcher getName() {
		return nameMatcher;
	}

	public void setName(NameMatcher nameMatcher) {
		if (nameMatcher == null)
            return;
		this.nameMatcher = nameMatcher;
	}

	private EnchantmentSetMatcher enchantmentMatcherAny =
			new EnchantmentSetMatcher(Collections.singletonList(new AnyEnchantment()));

	public EnchantmentSetMatcher getEnchantmentAny() {
		return enchantmentMatcherAny;
	}

	public void setEnchantmentAny(EnchantmentSetMatcher enchantmentMatcher) {
		if (enchantmentMatcher == null)
			return;
		this.enchantmentMatcherAny = enchantmentMatcher;
	}

	private EnchantmentSetMatcher enchantmentMatcherAll =
			new EnchantmentSetMatcher(Collections.singletonList(new AnyEnchantment()));

	public EnchantmentSetMatcher getEnchantmentAll() {
		return enchantmentMatcherAll;
	}

	public void setEnchantmentAll(EnchantmentSetMatcher enchantmentMatcherAll) {
		if (materialMatcher == null)
			return;
		this.enchantmentMatcherAll = enchantmentMatcherAll;
	}

	private AmountMatcher durabilityMatcher = new AnyAmount();

	public AmountMatcher getDurability() {
		return durabilityMatcher;
	}

	public void setDurability(AmountMatcher durabilityMatcher) {
		if (durabilityMatcher == null)
			return;
		this.durabilityMatcher = durabilityMatcher;
	}

	public class EnchantmentSetMatcher {
		public EnchantmentSetMatcher(List<EnchantmentMatcher> enchantmentMatchers) {
			this.enchantmentMatchers = enchantmentMatchers;
		}

		public List<EnchantmentMatcher> enchantmentMatchers;

		public boolean matches(Map<Enchantment, Integer> enchantments, boolean isAny) {
			if (enchantmentMatchers.size() == 1 && enchantmentMatchers.get(0) instanceof AnyEnchantment && enchantments.size() == 0)
				return true;

			for (EnchantmentMatcher matcher : enchantmentMatchers) {
				boolean matchedOne = false;

				for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
					Enchantment enchantment = entry.getKey();
					int level = entry.getValue();

					if (matcher.matches(enchantment, level)) {
						matchedOne = true;
						if (isAny)
							return true;
					}
				}

				if (!isAny && !matchedOne)
					return false;
			}

			if (!isAny)
				return true;
			else
				return false;
		}
	}
}
