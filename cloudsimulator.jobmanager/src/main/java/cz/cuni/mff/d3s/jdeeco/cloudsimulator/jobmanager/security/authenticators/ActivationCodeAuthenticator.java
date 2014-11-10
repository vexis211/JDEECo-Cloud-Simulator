package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.authenticators;

import org.springframework.security.core.Authentication;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.CodeAuthenticationToken;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.CustomUserDetails;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.CustomUserDetailsImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.SecurityService;

/**
 * Authenticates against reset password code.
 */
public class ActivationCodeAuthenticator implements CodeAuthenticator {

	/**
	 * Service for retrieving users by reset code.
	 */
	private final SecurityService securityService;

	/**
	 * Constructor.
	 * 
	 * @param securityService
	 *            Service for getting user info.
	 */
	public ActivationCodeAuthenticator(final SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public final Authentication authenticate(final CodeAuthenticationToken authentication) {
		User user = securityService.findByActivationCode(authentication.getCode());
		if (user == null) {
			return authentication;
		}

		CustomUserDetails principal = new CustomUserDetailsImpl(user);
		CodeAuthenticationToken authenticated = new CodeAuthenticationToken(authentication.getCodeType(),
				authentication.getCode(), principal, principal.getAuthorities());
		authenticated.setAuthenticated(true);
		return authenticated;
	}

}
