package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.color;

import org.bukkit.Color;

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
}
