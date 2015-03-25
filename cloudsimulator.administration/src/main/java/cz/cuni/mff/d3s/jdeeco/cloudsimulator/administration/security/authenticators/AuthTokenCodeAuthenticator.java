package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.authenticators;

import org.springframework.security.core.Authentication;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.AuthenticationToken;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.AuthenticationTokenService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.CodeAuthenticationToken;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.CustomUserDetails;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.CustomUserDetailsImpl;

public class AuthTokenCodeAuthenticator implements CodeAuthenticator {

	private AuthenticationTokenService authTokenService;

	public AuthTokenCodeAuthenticator(AuthenticationTokenService authTokenService) {
		this.authTokenService = authTokenService;
	}

	@Override
	public final Authentication authenticate(final CodeAuthenticationToken authentication) {
		AuthenticationToken authToken = authTokenService.findByValue(authentication.getCode());
		if (authToken == null) {
			return authentication;
		}
		User user = authToken.getUser();
		CustomUserDetails principal = new CustomUserDetailsImpl(user);
		CodeAuthenticationToken authenticated = new CodeAuthenticationToken(authentication.getCodeType(),
				authentication.getCode(), principal, principal.getAuthorities());
		authenticated.setAuthenticated(true);
		return authenticated;
	}

}
