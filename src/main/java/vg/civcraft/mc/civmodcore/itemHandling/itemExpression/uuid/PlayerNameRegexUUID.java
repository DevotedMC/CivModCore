package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Ameliorate
 */
public class PlayerNameRegexUUID implements UUIDMatcher {
	public PlayerNameRegexUUID(Pattern pattern) {
		this.pattern = pattern;
	}

	public Pattern pattern;

	@Override
	public boolean matches(UUID uuid) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
		if (player.getName() != null) {
			String name = player.getName();
			return pattern.matcher(name).matches();
		} else
			return false;
	}

	@Override
	public UUID solve(UUID startingValue) throws NotSolvableException {
		throw new NotSolvableException("can't solve a regex");
	}
}
