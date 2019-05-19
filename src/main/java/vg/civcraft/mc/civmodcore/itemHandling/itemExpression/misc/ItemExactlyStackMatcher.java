package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;

public class ItemExactlyStackMatcher implements ItemMatcher {
	public ItemExactlyStackMatcher(ItemStack itemStack) {
		this(itemStack, false);
	}

	public ItemExactlyStackMatcher(ItemStack itemStack, boolean acceptSimilar) {
		this.itemStack = itemStack;
		this.acceptSimilar = acceptSimilar;
	}

	public ItemStack itemStack;
	public boolean acceptSimilar;

	@Override
	public boolean matches(ItemStack item) {
		if (!acceptSimilar)
			return itemStack.equals(item);
		else
			return itemStack.isSimilar(item);
	}

	@Override
	public ItemStack solve(ItemStack defaultValue) throws NotSolvableException {
		return itemStack.clone();
	}
}
