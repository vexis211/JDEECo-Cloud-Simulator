package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions;

import java.text.Normalizer;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * String helper.
 */
public abstract class StringEx {
	private static final String SAFE_PATTERN = "[^-a-zA-Z_0-9]+";
	private static final String REMOVE_ACCENT_PATTERN = "\\p{InCombiningDiacriticalMarks}+";
	private static final String RANDOM_PATTERN = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String DOTS = "...";

	/**
	 * Check if string is null or empty.
	 * 
	 * @param text
	 *            String.
	 * @return True on success or false if contains some characters.
	 */
	public static boolean isNullOrEmpty(final String text) {
		return text == null || text.isEmpty();
	}

	/**
	 * Check if string is null or empty or contains only whitespaces.
	 * 
	 * @param text
	 *            String.
	 * @return True on success or false if contains any non-whitespace character.
	 */
	public static boolean isNullOrWhitespace(final String text) {
		return text == null || text.isEmpty() || text.trim().isEmpty();
	}

	/**
	 * Checks if the string is null or empty.
	 * 
	 * @param string
	 *            String to be checked for emptiness.
	 * @return {@code True} if the string is empty or contains only white space.
	 */
	public static boolean isEmpty(final String string) {
		return string == null || string.trim().length() == 0;
	}

	/**
	 * Makes the first letter lower case.
	 * 
	 * @param string
	 *            String that will have its first letter in lowercase form.
	 * @return First letter in lowercase.
	 */
	public static String firstToLower(final String string) {
		if (isEmpty(string)) {
			return string;
		} else {
			StringBuilder b = new StringBuilder(string);
			b.setCharAt(0, Character.toLowerCase(string.charAt(0)));
			return b.toString();
		}
	}

	/**
	 * Makes the first letter upper case.
	 * 
	 * @param string
	 *            String that will have its first letter in upper form.
	 * @return First letter in upper.
	 */
	public static String firstToUpper(final String string) {
		if (isEmpty(string)) {
			return string;
		} else {
			StringBuilder b = new StringBuilder(string);
			b.setCharAt(0, Character.toUpperCase(string.charAt(0)));
			return b.toString();
		}
	}

	/**
	 * Returns string containing only letters, numbers, '-' and '_'.
	 * 
	 * @param s
	 *            String containing any character.
	 * @return String from which any other than mentioned characters are removed.
	 */
	public static String getSafeString(String s) {
		String noAccent = removeAccent(s);
		Pattern pattern = Pattern.compile(SAFE_PATTERN);
		return pattern.matcher(noAccent).replaceAll("");
	}

	/**
	 * Removes accent from given string.
	 * 
	 * @param s
	 *            String containing accent characters.
	 * @return String without accent characters.
	 */
	public static String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile(REMOVE_ACCENT_PATTERN);
		return pattern.matcher(temp).replaceAll("");
	}

	/**
	 * Truncates a String to the given length.
	 * 
	 * @param value
	 *            String to be truncated
	 * @param length
	 *            Maximum length of string
	 * @return Returns value if value is null or value.length() is less or equal to length, otherwise a String
	 *         representing value truncated to length with added dots (...)
	 */
	public static String truncate(String value, int length) {
		if (value != null && value.length() > length) {
			value = value.substring(0, length);
			value += DOTS;
		}
		return value;
	}

	/**
	 * Generate random string for password
	 * 
	 * @param randomPasswordLength
	 *            Length
	 * @return Random string of specified length
	 */
	public static String getRandomString(int randomPasswordLength) {
		return getRandomString(randomPasswordLength, RANDOM_PATTERN);
	}

	/**
	 * Generate random string for password consisting of specified chars
	 * 
	 * @param randomPasswordLength
	 *            Length
	 * @param characters
	 *            String defining characters to use
	 * @return Random string of specified length containing specified characters only
	 */
	public static String getRandomString(int randomPasswordLength, String characters) {
		Random rng = new Random();

		char[] text = new char[randomPasswordLength];
		for (int i = 0; i < randomPasswordLength; ++i) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	public static <T> String join(String separator, Iterable<T> iterable) {
		StringBuilder builder = new StringBuilder();
		
		boolean firstItemWritten = false;
		for (T item : iterable) {
			if (firstItemWritten) {
				builder.append(separator);
				firstItemWritten = true;
			}
			builder.append(item);
		}
		
		return builder.toString();
	}

}
