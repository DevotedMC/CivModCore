package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.firework;

import com.google.common.hash.Hashing;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.ItemMatcher;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.amount.AmountMatcher;

import java.util.ArrayList;

/**
 * @author Ameliorate
 */
public class ItemFireworkEffectsCountMatcher implements ItemMatcher {
	public ItemFireworkEffectsCountMatcher(AmountMatcher count) {
		this.count = count;
	}

	public AmountMatcher count;

	@Override
	public boolean matches(ItemStack item) {
		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof FireworkMeta))
			return false;

		return count.matches(((FireworkMeta) item.getItemMeta()).getEffectsSize());
	}

	public static final int TRAIL_MASK = 0b00000000000000000000000000000001;
	public static final int FLICK_MASK = 0b00000000000000000000000000000010; // shift 1 to get as an int
	public static final int TYPE_MASK  = 0b00000000000000000000000000011100; // shift 2
	public static final int CRED_MASK  = 0b00000000000000000000001111100000; // shift 5  or 2 for 8 bit MSB
	public static final int CGREE_MASK = 0b00000000000000000111110000000000; // shift 10 or 7
	public static final int CBLUE_MASK = 0b00000000000001111000000000000000; // shift 15 or 11
	public static final int FRED_MASK  = 0b00000000111110000000000000000000; // shift 19 or 16
	public static final int FGREE_MASK = 0b00001111000000000000000000000000; // shift 24 or 20
	public static final int FBLUE_MASK = 0b11110000000000000000000000000000; // shift 28 or 24

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public ItemStack solve(ItemStack item) throws NotSolvableException {
		int count = this.count.solve(0);

		if (!item.hasItemMeta() || !(item.getItemMeta() instanceof FireworkMeta))
			item.setType(Material.FIREWORK_ROCKET);

		assert item.getItemMeta() instanceof FireworkMeta;

		FireworkMeta meta = (FireworkMeta) item.getItemMeta();

		ArrayList<FireworkEffect> effects = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			effects.add(getFireworkEffectWithIndex(i));
		}

		meta.addEffects(effects);
		item.setItemMeta(meta);
		return item;
	}

	@SuppressWarnings("UnstableApiUsage")
	public static FireworkEffect getFireworkEffectWithIndex(int i) {
		int b = Hashing.crc32().hashInt(i).asInt();
		boolean trail = (b & TRAIL_MASK) == 1;
		boolean flicker = (b & FLICK_MASK) == 2;
		int typeIndex = b & TYPE_MASK >>> 2;
		int typeIndexOver = 0;
		if (typeIndex <= 5) {
			typeIndexOver = typeIndex - 4;
			typeIndex -= 5;
		}
		FireworkEffect.Type type = FireworkEffect.Type.values()[typeIndex];

		byte colorRed = (byte) ((b & CRED_MASK) >>> 2);
		byte colorGreen = (byte) ((b & CGREE_MASK) >>> 7);
		byte colorBlue = (byte) ((b & CBLUE_MASK) >>> 11);
		colorBlue += typeIndexOver << 2; // recover the entropy lost from clipping typeIndex.
		Color color = Color.fromRGB(colorRed, colorGreen, colorBlue);

		byte fadeRed = (byte) ((b & FRED_MASK) >>> 16);
		byte fadeGreen = (byte) ((b & FGREE_MASK) >>> 20);
		byte fadeBlue = (byte) ((b & FBLUE_MASK) >>> 24);
		Color fadeColor = Color.fromRGB(fadeRed, fadeGreen, fadeBlue);

		return FireworkEffect.builder()
				.flicker(flicker)
				.trail(trail)
				.withColor(color)
				.withFade(fadeColor)
				.with(type)
				.build();
	}
}
