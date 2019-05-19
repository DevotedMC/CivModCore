package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

import com.google.common.collect.Lists;
import vg.civcraft.mc.civmodcore.itemHandling.itemExpression.Matcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
	public <T, M extends Matcher<T>> boolean matches(Collection<M> matchers, Collection<T> matched) {
		Stream<M> matcherStream = matchers.stream();
		Predicate<M> matchedPredicate = (matcher) -> matched.stream().anyMatch(matcher::matches);

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
						if (matcher.matches(matchedElement)) {
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

	public <T, M extends Matcher<T>> List<T> solve(Collection<M> matchers, Supplier<T> defaultValue)
			throws Matcher.NotSolvableException {
		ArrayList<T> result = new ArrayList<>();

		switch (this) {
			case ONE_TO_ONE:
			case ALL:
				for (M matcher : matchers) {
					result.add(matcher.solve(defaultValue.get()));
				}

				break;

			case ANY:
				List<Matcher.NotSolvableException> causes = new ArrayList<>();
				boolean hasSolved = false;
				for (Matcher<T> matcher : matchers) {
					try {
						result.add(matcher.solve(defaultValue.get()));
						hasSolved = true;
						break;
					} catch (Matcher.NotSolvableException e) {
						causes.add(e);
					}
				}

				if (!hasSolved) {
					Matcher.NotSolvableException e = new Matcher.NotSolvableException("while solving for any matching");
					causes.forEach(e::addSuppressed);
					throw e;
				}

				break;

			case NONE:
				throw new Matcher.NotSolvableException("can't yet do negative solving");
		}

		return result;
	}

	// TODO: how to make this class for non-entry:
	// use object reflection to optain all fields of defaultValue. use reflection to obtain all fields of returnedValue.
	// use Object.== operator to compare reference equality. Consiter an entry taken if any of the feilds between the
	// two are equal.
	public static class LazyFromListEntrySupplier<K, V> implements Supplier<Map.Entry<K, V>> {
		public LazyFromListEntrySupplier(Supplier<Collection<Map.Entry<K, V>>> entriesSupplier) {
			this.entriesSupplier = entriesSupplier;
		}

		public LazyFromListEntrySupplier(Collection<Map.Entry<K, V>> collection) {
			this(() -> new ArrayList<>(collection));
		}

		public LazyFromListEntrySupplier(Map<K, V> map) {
			this(map.entrySet());
		}

		private void regen() {
			if (entries == null || entries.isEmpty())
				entries = entriesSupplier.get();
		}

		public Collection<Map.Entry<K, V>> entries;
		public Supplier<Collection<Map.Entry<K, V>>> entriesSupplier;

		@Override
		public Map.Entry<K, V> get() {
			return new Map.Entry<K, V>() {
					private K e;
					private V l;
					private boolean taken = false;

					private void evaluate() {
						if (!taken) {
							regen();
							Map.Entry<K, V> entry = entries.stream().limit(1).findFirst()
									.orElseThrow(NullPointerException::new);
							entries.remove(entry);

							e =  entry.getKey();
							l =  entry.getValue();

							taken = true;
						}
					}

					@Override
					public K getKey() {
						evaluate();
						return e;
					}

					@Override
					public V getValue() {
						evaluate();
						return l;
					}

					@Override
					public V setValue(V value) {
						throw new UnsupportedOperationException();
					}
				};
		}
	}
}
