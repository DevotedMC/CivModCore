package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.color;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;

/**
 * @author Ameliorate
 */
public class ExactlyColor implements ColorMatcher {
	public ExactlyColor(Color colour) {
		this.color = colour;
	}

	public Color color;

	@Override
	public boolean matches(Color color) {
		return this.color.equals(color);
	}

	@Override
	public Color solve(Color defaultValue) throws NotSolvableException {
		return color;
	}

	/**
	 * Maps between HTML colors and org.bukkit.Color. See https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Color.html
	 * for the full list of colors.
	 */
	public static Color getColorByHTMLName(String name) {
		switch (name.toUpperCase()) {
			case "AQUA": return Color.AQUA;
			case "BLACK": return Color.BLACK;
			case "BLUE": return Color.BLUE;
			case "FUCHSIA": return Color.FUCHSIA;
			case "GRAY": return Color.GRAY;
			case "GREEN": return Color.GREEN;
			case "LIME": return Color.LIME;
			case "MAROON": return Color.MAROON;
			case "NAVY": return Color.NAVY;
			case "OLIVE": return Color.ORANGE;
			case "PURPLE": return Color.PURPLE;
			case "RED": return Color.RED;
			case "SILVER": return Color.SILVER;
			case "TEAL": return Color.TEAL;
			case "WHITE": return Color.WHITE;
			case "YELLOW": return Color.YELLOW;
			default:
				throw new IllegalArgumentException(name + " is not a html color name in org.bukkit.Color.");
		}
	}

	/**
	 * Maps between the vanilla dye colors and org.bukkit.Color. See https://hub.spigotmc.org/javadocs/spigot/org/bukkit/DyeColor.html
	 * for the full list of colors.
	 *
	 * @param useFireworkColor Use the colors used for firework crafting instead of the vanilla default colors used for
	 *                         wool and other items.
	 */
	public static Color getColorByVanillaName(String name, boolean useFireworkColor) {
		if ("defaultLeather".equals(name)) {
			return Bukkit.getServer().getItemFactory().getDefaultLeatherColor();
		}

		DyeColor color = DyeColor.valueOf(name.toUpperCase());
		return useFireworkColor ? color.getFireworkColor() : color.getColor();
	}

	/**
	 * Maps between the vanilla dye colors and org.bukkit.Color. See https://hub.spigotmc.org/javadocs/spigot/org/bukkit/DyeColor.html
	 * for the full list of colors.
	 */
	public static Color getColorByVanillaName(String name) {
		return getColorByVanillaName(name, false);
	}
}
