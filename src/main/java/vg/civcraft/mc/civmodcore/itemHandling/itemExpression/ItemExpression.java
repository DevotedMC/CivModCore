package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
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
	 * @param configurationSection The config that options will be taken from.
	 */
	public void parseConfig(ConfigurationSection configurationSection) {
		String materialString = configurationSection.getString("material", "any");
		if (materialString.equals("any"))
			setMaterial(new AnyMaterial());
		else if (materialString.startsWith("regex ")) {
			String regex = materialString.replaceFirst("^regex ", "");
			Pattern pattern = Pattern.compile(regex);
			setMaterial(new RegexMaterial(pattern));
		} else
			setMaterial(new ExactlyMaterial(Material.getMaterial(materialString)));
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
}
