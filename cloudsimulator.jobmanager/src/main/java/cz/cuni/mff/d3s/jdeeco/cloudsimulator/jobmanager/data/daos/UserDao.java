package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;

public interface UserDao extends BaseDao<User> {

	/**
	 * Find user by email
	 * 
	 * @param email
	 *            Email to search by
	 * @return Matching user object, or null when no such exists
	 */
	User findByEmail(String email);

	/**
	 * Check whenever the user is activated
	 * 
	 * @param user
	 *            User to be checked
	 * @return True when activated, false otherwise
	 */
	boolean isUserActivated(User user);

	/**
	 * Set user activated flag
	 * 
	 * @param user
	 *            User to modify
	 * @param isActivated
	 *            Whenever the activation flag should be set or no
	 */
	void setIsUserActivated(User user, boolean isActivated);

	/**
	 * Check whenever the user is registered
	 * 
	 * @param user
	 *            User to be checked
	 * @return True when registered, false otherwise
	 */
	boolean isUserRegistered(User user);

	/**
	 * Set user registered flag
	 * 
	 * @param user
	 *            User to modify
	 * @param isActivated
	 *            Whenever the registration flag should be set or no
	 */
	void setIsUserRegistered(User user, boolean isRegistered);

	/**
	 * Check whenever the user is administrator
	 * 
	 * @param user
	 *            user to check
	 * 
	 * @return
	 */
	boolean isUserAdmin(User user);
}
