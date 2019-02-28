package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid;

import java.util.UUID;

/**
 * @author Ameliorate
 */
public class ExactlyUUID implements UUIDMatcher {
	public ExactlyUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID uuid;

	@Override
	public boolean matches(UUID uuid) {
		return this.uuid.equals(uuid);
	}
}
