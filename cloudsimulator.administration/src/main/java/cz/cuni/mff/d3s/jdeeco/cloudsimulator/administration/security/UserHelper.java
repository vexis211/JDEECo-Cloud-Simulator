package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;

/**
 * Common user operations used by multiple services.
 */
public final class UserHelper {

	/**
	 * Utility class constructor.
	 */
	private UserHelper() {
		// Prevents constructing.
	}

	/**
	 * Retrieves current logged in user for this request thread or null if such user not exists.
	 * 
	 * @return User or null.
	 */
	public static User getAuthenticatedUser() {

		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return null;
		}
		Authentication auth = context.getAuthentication();
		if (auth == null) {
			// No user authenticated.
			return null;
		}
		Object principal = auth.getPrincipal();
		if (principal instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
			return userDetails.getUser();
		}
		// User is not authenticated.
		return null;
	}
}
