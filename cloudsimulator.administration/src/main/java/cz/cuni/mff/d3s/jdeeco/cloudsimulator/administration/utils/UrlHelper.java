package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlHelper {
	/** Default encoding */
	private static final String ENCODING = "UTF-8";

	/**
	 * Add parameter to URL
	 * 
	 * @param URL
	 *            Source url
	 * @param name
	 *            Parameter name
	 * @param value
	 *            paramteter value
	 * @return URL with parameter
	 */
	public static String addParameter(String URL, String name, String value) {
		int qpos = URL.indexOf('?');
		int hpos = URL.indexOf('#');
		char sep = qpos == -1 ? '?' : '&';
		String seg = sep + encodeUrl(name) + '=' + encodeUrl(value);
		return hpos == -1 ? URL + seg : URL.substring(0, hpos) + seg + URL.substring(hpos);
	}

	/**
	 * The same behaviour as Web.escapeUrl, only without the "funky encoding" of the characters ? and ;.
	 * 
	 * @param url
	 *            The string to encode.
	 * @return <code>url</code> fully escaped using URL rules.
	 */
	public static String encodeUrl(String url) {
		try {
			return URLEncoder.encode(url, ENCODING);
		} catch (UnsupportedEncodingException uee) {
			throw new IllegalArgumentException(uee);
		}
	}
}
