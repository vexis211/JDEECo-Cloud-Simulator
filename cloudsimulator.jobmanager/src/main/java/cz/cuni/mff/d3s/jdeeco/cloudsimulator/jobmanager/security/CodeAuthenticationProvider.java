package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.authenticators.CodeAuthenticator;

/**
 * Provides authentication for CodeAuthentication Tokens.
 */
public class CodeAuthenticationProvider implements AuthenticationProvider {

	/**
	 * Registered authenticators for various code types used to authenticate tokens.
	 */
	private HashMap<AuthenticationCodeType, CodeAuthenticator> authenticators;

	/**
	 * Authenticates token and fills in additional data.
	 * 
	 * @param authentication
	 *            CodeaAuthenticationToken to authenticate.
	 * @return Returns authenticated object or null if cannot authenticate.
	 */
	@Override
	public final Authentication authenticate(final Authentication authentication) {
		CodeAuthenticationToken codeAuthentication = (CodeAuthenticationToken) authentication;
		if (!getAuthenticators().containsKey(codeAuthentication.getCodeType())) {
			// Not supported code.
			throw new CodeTypeNotSupportedException(codeAuthentication.getCodeType());
		}
		CodeAuthenticator authenticator = getAuthenticators().get(codeAuthentication.getCodeType());
		Authentication authenticated = authenticator.authenticate(codeAuthentication);
		return authenticated;
	}

	/**
	 * Check if class supports this authentication type.
	 * 
	 * @param authentication
	 *            Authentication type.
	 * @return True if type is supported otherwise false.
	 */
	@Override
	public final boolean supports(final Class<?> authentication) {
		return authentication.equals(CodeAuthenticationToken.class);
	}

	/**
	 * Available authenticators.
	 * 
	 * @return Available authenticators hashmap.
	 */
	public final HashMap<AuthenticationCodeType, CodeAuthenticator> getAuthenticators() {
		return authenticators;
	}

	/**
	 * Sets available authenticators.
	 * 
	 * @param authenticators
	 *            New available authenticators. must not be null.
	 */
	public final void setAuthenticators(final HashMap<AuthenticationCodeType, CodeAuthenticator> authenticators) {
		this.authenticators = authenticators;
	}

}
