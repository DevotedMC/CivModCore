package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Represents the different ways of interpreting a list of things when comparing it to another list.
 *
 * @author Ameliorate
 */
public enum ListMatchingMode {
	/**
	 * At least one element in the list must match an element in the other list.
	 */
	ANY("Any", "any"),

	/**
	 * Every one of the elements in the list must match an element in the other list.
	 */
	ALL("All", "all"),

	/**
	 * No element in the list may match an element in the other list.
	 */
	NONE("None", "none"),

	/**
	 * Try to match each element to another element in the other list 1:1, where no element in either list has a
	 * counterpart in the other list. Returns true when every item in the first list matched an item in the second
	 * list.
	 *
	 * This is like ALL, but in ALL an item in the first list can match multiple items in the second list. In this mode,
	 * an item in the first list can only match one item in the second list.
	 */
	ONE_TO_ONE("OneToOne", "oneToOne");

	ListMatchingMode(String upperCamelCase, String lowerCamelCase) {
		this.upperCamelCase = upperCamelCase;
		this.lowerCamelCase = lowerCamelCase;
	}

	/**
	 * Returns the name of this mode, in CamelCase starting with an upper case letter.
	 *
	 * For example: ONE_TO_ONE becomes OneToOne.
	 *
	 * @return The name of the mode, in UpperCammelCase.
	 */
	public String getUpperCamelCase() {
		return upperCamelCase;
	}

	/**
	 * Returns the name of this mode, in CamelCase starting with a lower case letter.
	 *
	 * For example: ONE_TO_ONE becomes oneToOne.
	 *
	 * @return The name of the mode, in lowerCamelCase.
	 */
	public String getLowerCamelCase() {
		return lowerCamelCase;
	}

	private String upperCamelCase;
	private String lowerCamelCase;

	/**
	 * Generic function to exxert this ListMatchingMode in matching a list of things using a list of matchers.
	 *
	 * @param matchers The list of matchers that will match over elements of the list of things.
	 * @param matched The list of things that the list of matchers will match over.
	 * @param <T> The type of the list of things.
	 * @param <M> The type of each matcher.
	 * @return If the list of matchers matched over the list of things, in the order that was defined in this ListMatchingMode.
	 */
	public <T, M extends GenericMatcher<T>> boolean matches(Collection<M> matchers, Collection<T> matched) {
		Stream<M> matcherStream = matchers.stream();
		Predicate<M> matchedPredicate = (matcher) -> matched.stream().anyMatch(matcher::genericMatches);

		switch (this) {
			case ANY:
				return matcherStream.anyMatch(matchedPredicate);
			case ALL:
				return matcherStream.allMatch(matchedPredicate);
			case NONE:
				return matcherStream.noneMatch(matchedPredicate);
			case ONE_TO_ONE:
				List<M> matchersClone = new ArrayList<>(matchers);
				List<T> matchedClone = new ArrayList<>(matched);

				for (M matcher : Lists.reverse(matchersClone)) {
					boolean hasMatched = false;

					for (T matchedElement : matchedClone) {
						if (matcher.genericMatches(matchedElement)) {
							matchedClone.remove(matchedElement);
							matchersClone.remove(matcher);
							hasMatched = true;
							break;
						}
					}

					if (!hasMatched)
						return false;
				}

				return matchersClone.isEmpty();
		}

		throw new AssertionError("not reachable");
	}
}
