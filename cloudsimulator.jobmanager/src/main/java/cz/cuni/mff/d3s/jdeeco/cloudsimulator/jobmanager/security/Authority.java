package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Spring security GrantedAuthority implementation. Using conversion between
 * spring security string roles and int values from model.
 */
public class Authority implements GrantedAuthority {

	/**
	 * Serializable parameter.
	 */
	private static final long serialVersionUID = -1801125706530036774L;

	/**
	 * Role name.
	 */
	private String role;

	/**
	 * Role id/name from model.
	 */
	private int roleId;

	/**
	 * Prevents constructing authority without defining role.
	 */
	@SuppressWarnings("unused")
	private Authority() {
	}

	/**
	 * Constructor.
	 * 
	 * @param role
	 *            Role name.
	 */
	public Authority(final String role) {
		this.role = role;
	}

	/**
	 * Constructor.
	 * 
	 * @param roleId
	 *            Role id/name from model.
	 */
	public Authority(final int roleId) {
		this.role = RoleConvertor.getRole(roleId);
		this.roleId = roleId;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (!(obj instanceof Authority)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		return this.roleId == ((Authority) obj).roleId;
	}

	@Override
	public final int hashCode() {
		return this.roleId;
	}

	@Override
	public final String getAuthority() {
		return role;
	}

}
