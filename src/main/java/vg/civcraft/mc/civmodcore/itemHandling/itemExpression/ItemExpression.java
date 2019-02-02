package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AnyAmount;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.ExactlyAmount;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.RangeAmount;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore.AnyLore;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore.ExactlyLore;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore.LoreMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore.RegexLore;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.AnyMaterial;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.ExactlyMaterial;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.MaterialMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.RegexMaterial;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.name.*;

import java.util.ArrayList;
import java.util.List;
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
		parseMaterial(config);
		parseAmount(config);
		parseLore(config);
		parseName(config);
	}

	private void parseMaterial(ConfigurationSection config) {
		if (config.contains("material.regex"))
			setMaterial(new RegexMaterial(Pattern.compile(config.getString("material.regex"))));
		else if ("any".equals(config.getString("material")))
			// yoda order because config.getString is null if doesn't exist
			setMaterial(new AnyMaterial());
		else if (config.contains("material"))
			setMaterial(new ExactlyMaterial(Material.getMaterial(config.getString("material"))));
	}

	private void parseAmount(ConfigurationSection config) {
		if (config.contains("amount.range"))
			setAmount(new RangeAmount(
					config.getInt("amount.range.low", 0),
					config.getInt("amount.range.high"),
					config.getBoolean("amount.range.inclusiveLow", true),
					config.getBoolean("amount.range.inclusiveHigh", true)));
		else if ("any".equals(config.getString("amount")))
			setAmount(new AnyAmount());
		else if (config.contains("amount"))
			setAmount(new ExactlyAmount(config.getInt("amount")));
	}

	private void parseLore(ConfigurationSection config) {
		if (config.contains("lore.regex")) {
			List<String> patternsStr = config.getStringList("lore.regex");
			List<Pattern> patterns = new ArrayList<>();
			boolean multiline = config.getBoolean("lore.regexMultiline", true);

			for (String patternStr : patternsStr) {
				patterns.add(Pattern.compile(patternStr, multiline ? Pattern.MULTILINE : 0));
			}
			setLore(new RegexLore(patterns));
		} else if ("any".equals(config.getString("lore")))
			setLore(new AnyLore());
		else if (config.contains("lore"))
			setLore(new ExactlyLore(config.getStringList("lore")));
	}

	private void parseName(ConfigurationSection config) {
		if (config.contains("name.regex"))
			setName(new RegexName(Pattern.compile(config.getString("name.regex"))));
		else if ("any".equals(config.getString("name")))
			setName(new AnyName());
		else if ("vanilla".equals(config.getString("name")))
			setName(new VanillaName());
		else if (config.contains("name"))
			setName(new ExactlyName(config.getString("name")));
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
		else if (!item.hasItemMeta() && !(loreMatcher instanceof AnyLore) && !(nameMatcher instanceof AnyLore))
			// slightly gross, but passing in null if !hasItemMeta is also kinda gross
			// the code here wouldn't look nice either
			// java null chaining operator when?.
			return false;
		else if (!loreMatcher.matches(item.getItemMeta().getLore()))
			return false;
		else if (!nameMatcher.matches(item.getItemMeta().getDisplayName()))
			return false;
		return true;
	}

	private MaterialMatcher materialMatcher = new AnyMaterial();

	public MaterialMatcher getMaterial() {
		return materialMatcher;
	}

	public void setMaterial(MaterialMatcher materialMatcher) {
		this.materialMatcher = materialMatcher;
	}

	private AmountMatcher amountMatcher = new AnyAmount();

	public AmountMatcher getAmount() {
		return amountMatcher;
	}

	public void setAmount(AmountMatcher amountMatcher) {
		this.amountMatcher = amountMatcher;
	}

	private LoreMatcher loreMatcher = new AnyLore();

	public LoreMatcher getLore() {
		return loreMatcher;
	}

	public void setLore(LoreMatcher loreMatcher) {
		this.loreMatcher = loreMatcher;
	}

	private NameMatcher nameMatcher;

	public NameMatcher getName() {
		return nameMatcher;
	}

	public void setName(NameMatcher nameMatcher) {
		this.nameMatcher = nameMatcher;
	}
}
