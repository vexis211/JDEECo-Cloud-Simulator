package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.utils;

/**
 * Common HTML generators.
 */
public final class HTMLHelper {

	/**
	 * Prevents constructing helper class.
	 */
	private HTMLHelper() {

	}

	/**
	 * Constructors HTML A element.
	 * 
	 * @param href
	 *            Href part.
	 * @param text
	 *            Text part.
	 * @return A element string.
	 */
	public static String createLink(final String href, final String text) {
		return String.format("<a href=\"%s\">%s</a>", href, text);
	}

}
