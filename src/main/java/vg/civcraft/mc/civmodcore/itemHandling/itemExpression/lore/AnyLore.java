package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.lore;

import java.util.List;

/**
 * @author Ameliorate
 */
public class AnyLore implements LoreMatcher {
	@Override
	public boolean matches(List<String> lore) {
		return true;
	}
}
