package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirects with post and parameters.
 */
public class FormRedirectResponse {

	/**
	 * Post parameters.
	 */
	private Map<String, Object> parameters;

	/**
	 * Form action.
	 */
	private String action;

	/**
	 * Create redirect with empty action and parameters.
	 */
	public FormRedirectResponse() {
		parameters = new HashMap<String, Object>();
	}

	/**
	 * Create redirect with preset action and form parameters.
	 * 
	 * @param action
	 *            Form action.
	 * @param parameters
	 *            Post parameters.
	 */
	private FormRedirectResponse(final String action, final Map<String, Object> parameters) {
		this.action = action;
		this.parameters = parameters;
	}

	/**
	 * Constructs new instance with empty parameters and provided action.
	 * 
	 * @param action
	 *            Action.
	 * @return New instance with action.
	 */
	public static FormRedirectResponse initWithAction(final String action) {
		return new FormRedirectResponse(action, new HashMap<String, Object>());
	}

	/**
	 * Adds parameter.
	 * 
	 * @param name
	 *            Parameter name.
	 * @param value
	 *            Parameter value.
	 * @return Fluent interface instance.
	 */
	public final FormRedirectResponse addParameter(final String name, final Object value) {
		parameters.put(name, value);
		return this;
	}

	/**
	 * Renders html of redirect with post form to provided response.
	 * 
	 * @param response
	 *            Http response object.
	 * @throws IOException
	 *             On error writing to stream.
	 */
	public final void renderWithHeaders(final HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		render(response.getOutputStream());
	}

	/**
	 * Renders html of redirect with post form to provided stream.
	 * 
	 * @param stream
	 *            Output servlet stream.
	 * @throws IOException
	 *             On error writing to stream.
	 */
	public final void render(final ServletOutputStream stream) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		builder.append("<body onload=\"document.forms['form'].submit()\">");
		builder.append(String.format("<form name=\"form\" action=\"%s\" method=\"post\">", getAction()));
		for (String key : parameters.keySet()) {
			builder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\" />", key, parameters.get(key)));
		}
		builder.append("</form>");
		builder.append("</body>");
		builder.append("</html>");
		stream.print(builder.toString());
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
