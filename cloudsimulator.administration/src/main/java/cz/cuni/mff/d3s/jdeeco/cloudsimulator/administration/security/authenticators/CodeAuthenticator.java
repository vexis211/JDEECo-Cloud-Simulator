package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.authenticators;

import org.springframework.security.core.Authentication;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.CodeAuthenticationToken;

/**
 * Validates specific type of code. For example implementation AnonymousCodeAuthenticator.
 * 
 * @see AnonymousLoginCodeAuthenticator
 */
public interface CodeAuthenticator {

	/**
	 * Verifies code and sets principal and authorities data on success. Throws AuthenticationException if failed.
	 * 
	 * @param authentication
	 *            token to verify.
	 * @return Authenticated and fully filled Authentication.
	 */
	Authentication authenticate(CodeAuthenticationToken authentication);
}
