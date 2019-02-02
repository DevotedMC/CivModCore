package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore;

import java.util.List;

/**
 * @author Ameliorate
 */
public class ExactlyLore implements LoreMatcher {
	public ExactlyLore(List<String> lore) {
		this.lore = lore;
	}

	public List<String> lore;

	@Override
	public boolean matches(List<String> lore) {
		return this.lore.equals(lore);
	}
}
