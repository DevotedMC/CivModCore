package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.enchantment;

/**
 * Represents the different holders of enchantments an item can have.
 *
 * @author Ameliorate
 */
public enum EnchantmentsSource {
	/**
	 * The normal enchantments on the item that take effect. For example a diamond sword with Sharpness 5.
	 */
	ITEM,

	/**
	 * For example from the enchantments held inside an enchanted book
	 */
	HELD,
}
