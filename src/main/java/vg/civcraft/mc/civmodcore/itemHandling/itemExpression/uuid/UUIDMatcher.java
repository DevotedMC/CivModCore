package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid;

import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc.GenericMatcher;

import java.util.UUID;

/**
 * @author Ameliorate
 *
 * These classes are generally used for matching player heads.
 */
public interface UUIDMatcher extends GenericMatcher<UUID> {
	boolean matches(UUID uuid);

	@Override
	default boolean genericMatches(UUID matched) {
		return matches(matched);
	}
}
