package vg.civcraft.mc.civmodcore.itemHandling.itemExpression.misc;

/**
 * Represents any interface for matching over a class. This is used in the implenation of
 * ListMatcherMode.matches() in order to be able to accept any list of matchers and list of things.
 *
 * @param <T> The thing that is matched over by this matcher.
 *
 * @author Ameliorate
 */
public interface GenericMatcher<T> {
	/**
	 * Matches over the thing.
	 *
	 * In most *Matcher interfaces, this should be defined as a default method that calls the interface's individual
	 * matches() function.
	 *
	 * @param matched The thing that this matcher is matching over.
	 * @return If this matcher matched the thing.
	 */
	boolean genericMatches(T matched);
}
