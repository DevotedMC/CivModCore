package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid;

import java.util.UUID;

/**
 * @author Ameliorate
 *
 * These classes are generally used for matching player heads.
 */
public interface UUIDMatcher {
	boolean matches(UUID uuid);
}
