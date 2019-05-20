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

	/**
	 * Mutates the state of defaultValue such that matches(defaultValue) == true.
	 *
	 * If a value for startingValue where matches(defaultValue) == true, then NotSolvableException should be thrown.
	 * @param defaultValue Used as a "base" value for feilds that don't need changed for matches(defaultValue) to be true.
	 *                     This may or may not be mutated.
	 * @return The value that matches this matcher.
	 * @throws NotSolvableException If a state for defaultValue could not be found that matches.
	 */
	T solve(T defaultValue) throws NotSolvableException;

	/**
	 * Thrown if a certain matcher can not be solved.
	 *
	 * This might be thrown if the matcher is based on Regular Expressions, as most regular expressions can not be
	 * solved in reasonable time.
	 */
	class NotSolvableException extends Exception {
		public NotSolvableException(String message, Throwable cause) {
			super(message, cause);
		}

		public NotSolvableException(String message) {
			super(message);
		}
	}
}
