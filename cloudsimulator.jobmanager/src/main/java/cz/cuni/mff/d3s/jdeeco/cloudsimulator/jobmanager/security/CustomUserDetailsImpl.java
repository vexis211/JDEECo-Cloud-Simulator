package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.UserActivationFlags;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;

/**
 * Implementation of spring security UserDetails providing also underlying User
 * model.
 */
public class CustomUserDetailsImpl implements CustomUserDetails {

	/**
	 * Parameter of Serializable.
	 */
	private static final long serialVersionUID = -6283270485226448146L;

	/**
	 * Underlying user which belongs to this credentials.
	 */
	private User user;

	/**
	 * Prevents constructing instance without user.
	 */
	@SuppressWarnings("unused")
	private CustomUserDetailsImpl() {
	}

	/**
	 * Constructor.
	 * 
	 * @param user
	 *            User to be represented with this instance.
	 */
	public CustomUserDetailsImpl(final User user) {
		this.user = user;
	}

	/**
	 * Retrieves awarded authorities.
	 * 
	 * @return User's awarded authorities.
	 */
	@Override
	public final Collection<? extends GrantedAuthority> getAuthorities() {
		HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new Authority(getUser().getRole()));
		return authorities;
	}

	@Override
	public final String getPassword() {
		return getUser().getPassword();
	}

	@Override
	public final String getUsername() {
		return getUser().getEmail();
	}

	@Override
	public final boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public final boolean isAccountNonLocked() {
		return UserActivationFlags.hasFlags(getUser(), UserActivationFlags.IS_ACTIVATED);
	}

	@Override
	public final boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public final boolean isEnabled() {
		return true;
	}

	@Override
	public final User getUser() {
		return user;
	}
}
