package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AnyAmount;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.ExactlyAmount;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.RangeAmount;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.AnyMaterial;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.ExactlyMaterial;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.MaterialMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.material.RegexMaterial;

import java.util.regex.Pattern;

/**
 * A unified syntax for matching any ItemStack for things like the material, amount, lore contents, and more.
 *
 * While mostly designed to be used in a .yaml config file, this can also be used from java.
 */
public class ItemExpression {
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

	/**
	 * Creates the default ItemExpression.
	 *
	 * By default, it will match any ItemStack.
	 */
	public ItemExpression() {}

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
		else
			return true;
	}

	private MaterialMatcher materialMatcher = new AnyMaterial();

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
}
