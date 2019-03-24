package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid;

import java.util.UUID;

/**
 * @author Ameliorate
 */
public class AnyUUID implements UUIDMatcher {
	@Override
	public boolean matches(UUID matched) {
		return true;
	}
}
