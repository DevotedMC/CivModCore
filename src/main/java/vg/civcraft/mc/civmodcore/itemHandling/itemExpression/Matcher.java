package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

/**
 * Represents any interface for matching over a class.
 *
 * This is used mainly in the implementation of ListMatcherMode.matches()
 * in order to be able to accept any list of matchers and list of things.
 *
 * However, this is used by all the *Matcher's in order to define the boolean matches(TheThing) function.
 *
 * @param <T> The thing that is matched over by this matcher.
 *
 * @author Ameliorate
 */
public interface Matcher<T> {
	/**
	 * Determines if this Matcher matches the thing passed in.
	 *
	 * This should not mutate matched in any way.
	 *
	 * @param matched The thing that this matcher is matching over.
	 * @return If this matcher matched the thing.
	 */
	boolean matches(T matched);
}
