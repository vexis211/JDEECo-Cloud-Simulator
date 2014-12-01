package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged.data;

import java.util.Date;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;

/**
 * Representation of various login codes.
 */
public class LoginCodeInfo {

	/**
	 * Code.
	 */
	private String code;

	/**
	 * Date of creation or refresh.
	 */
	private Date created;

	/**
	 * Owner of code.
	 */
	private User user;

	/**
	 * Constructor.
	 * 
	 * @param code
	 *            Code.
	 * @param created
	 *            Date of creation or refresh.
	 * @param user
	 *            Owner.
	 */
	public LoginCodeInfo(final String code, final Date created, final User user) {
		this.code = code;
		this.created = created;
		this.user = user;
	}

	/**
	 * Creates instance which should delete code on passing to code setter.
	 * 
	 * @param user
	 *            Owner of code.
	 * @return Deleting instance.
	 */
	public static LoginCodeInfo prepareDeleteCode(final User user) {
		return new LoginCodeInfo(null, null, user);
	}

	public final String getCode() {
		return code;
	}

	public final void setCode(String code) {
		this.code = code;
	}

	public final Date getCreated() {
		return created;
	}

	public final void setCreated(Date created) {
		this.created = created;
	}

	public final User getUser() {
		return user;
	}

	public final void setUser(User user) {
		this.user = user;
	}
}
