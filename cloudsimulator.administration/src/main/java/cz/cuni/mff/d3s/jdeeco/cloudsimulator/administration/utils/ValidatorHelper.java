package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.utils;

import java.util.regex.Pattern;

/**
 * Common validating features.
 */
public final class ValidatorHelper {

	/**
	 * Constructor. Prevents creating instance of helper class.
	 */
	private ValidatorHelper() {
	}

	/**
	 * Checks if email is in correct format.
	 * 
	 * @param email
	 *            Email name to check.
	 * @return True if is in correct format otherwise false.
	 */
	public static boolean isEmail(final String email) {
		return Pattern.matches(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email);
	}

	/**
	 * Checks if email is not in correct format.
	 * 
	 * @param email
	 *            Email name to check.
	 * @return False if is in correct format otherwise true.
	 */
	public static boolean isNotEmail(final String email) {
		return !isEmail(email);
	}
}
