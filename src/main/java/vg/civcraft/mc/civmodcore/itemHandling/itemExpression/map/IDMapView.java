package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.map;

import org.bukkit.map.MapView;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

/**
 * @author Ameliorate
 */
public class IDMapView implements MapViewMatcher {
	public IDMapView(AmountMatcher id) {
		this.id = id;
	}

	public AmountMatcher id;

	@Override
	public boolean matches(MapView map) {
		int id = map.getId();
		return this.id.matches(id);
	}
}
