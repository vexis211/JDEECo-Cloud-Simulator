package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Handles actions AJAX request failed on authentication. Returns 403 Forbidden for these requests otherwise redirects
 * to login.
 */
public class AjaxActionAuthenicationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	/**
	 * All AJAX action request must contain this header. Request is AJAX action when containing this header.
	 */
	public static final String AJAX_REQUEST_HEADER_KEY = "X-AjaxRequest";

	/**
	 * HTTP response code for failing authentication during AJAX action request.
	 */
	public static final int AJAX_AUTH_FAILED_RESPONSE_ERROR_CODE = 403;

	/**
	 * Constructor for LoginUrlAuthenticationEntryPoint.
	 * 
	 * @param loginUrl
	 *            URI from LoginUrlAuthenticationEntryPoint.
	 */
	public AjaxActionAuthenicationEntryPoint(final String loginUrl) {
		super(loginUrl);
	}

	@Override
	public final void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException authException) throws IOException, ServletException {
		boolean isAjax = request.getHeader(AJAX_REQUEST_HEADER_KEY) != null;
		if (isAjax) {
			response.sendError(AJAX_AUTH_FAILED_RESPONSE_ERROR_CODE);
		} else {
			super.commence(request, response, authException);
		}
	}

}
