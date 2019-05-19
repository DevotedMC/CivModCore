package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.color;

import org.bukkit.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Ameliorate
 */
public class ListColor implements ColorMatcher {
	public ListColor(List<ColorMatcher> matchers, boolean noneInList) {
		this.matchers = matchers;
		this.noneInList = noneInList;
	}

	public List<ColorMatcher> matchers;
	public boolean noneInList;

	@Override
	public boolean matches(Color color) {
		Stream<ColorMatcher> stream = matchers.stream();
		Predicate<ColorMatcher> predicate = (matcher) -> matcher.matches(color);

		if (noneInList) {
			return stream.noneMatch(predicate);
		} else {
			return stream.anyMatch(predicate);
		}
	}

	@Override
	public Color solve(Color defaultValue) throws NotSolvableException {
		if (!noneInList) {
			List<NotSolvableException> causes = new ArrayList<>();

			for (ColorMatcher matcher : matchers) {
				try {
					return matcher.solve(defaultValue);
				} catch (NotSolvableException e) {
					causes.add(e);
				}
			}

			NotSolvableException e = new NotSolvableException("couldn't solve list of color matchers");
			causes.forEach(e::addSuppressed);
			throw e;
		} else {
			// just brute force it, starting at defaultValue.

			// A brute force search is okay because you can only match over exactly color or in list color.
			// If you were to list every single color, ypu'd exaust memory on nearly every machine.

			Color color = defaultValue;
			while (!matches(color)) {
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();

				if (++red > 255) {
					red = 0;
					green++;
				}
				if (green > 255) {
					green = 0;
					blue++;
				}
				if (blue > 255) {
					blue = 0;
				}

				// we manually implement overflowing because java doesn't have a unsigned 24 bit type, and unsigned
				// bytes aren't easy either.

				color = Color.fromRGB(red, green, blue);

				if (color == defaultValue)
					throw new NotSolvableException("exausted entire rgb space searching for matching color");
			}

			return color;
		}
	}
}
