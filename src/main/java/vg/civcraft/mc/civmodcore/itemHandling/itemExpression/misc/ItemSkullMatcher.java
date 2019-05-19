package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.uuid.UUIDMatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author Ameliorate
 */
public class ItemSkullMatcher implements ItemMatcher {
	public ItemSkullMatcher(List<UUIDMatcher> ownerMatcher) {
		this.ownerMatcher = ownerMatcher;
	}

	public static ItemSkullMatcher construct(List<UUIDMatcher> ownerMatcher) {
		if (ownerMatcher == null || ownerMatcher.isEmpty())
			return null;

		return new ItemSkullMatcher(ownerMatcher);
	}

	public List<UUIDMatcher> ownerMatcher;

	@Override
	public boolean matches(ItemStack item) {
        if (!item.hasItemMeta() || !(item.getItemMeta() instanceof SkullMeta))
        	return false;
        UUID owner;
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (!meta.hasOwner())
        	owner = new UUID(0, 0);
        else
        	owner = meta.getOwningPlayer().getUniqueId();
        return ownerMatcher.stream().anyMatch((matcher) -> matcher.matches(owner));
	}

	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		UUID uuid = ListMatchingMode.ANY.solve(ownerMatcher,
				() -> new UUID(0, 0))
				.get(0);

		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof SkullMeta))
			item.setType(Material.PLAYER_HEAD);
		assert item.getItemMeta() instanceof SkullMeta;

		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
		item.setItemMeta(meta);
		return item;
	}
}
