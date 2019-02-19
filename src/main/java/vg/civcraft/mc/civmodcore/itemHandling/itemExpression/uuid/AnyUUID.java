package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid;

import java.util.UUID;

public class AnyUUID implements UUIDMatcher {
	@Override
	public boolean matches(UUID uuid) {
		return true;
	}
}
