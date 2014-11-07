package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;

public interface UserService extends UserDetailsService {

	/**
	 * Retrieves user by id.
	 * 
	 * @param userId
	 *            User's id.
	 * @return User or null if not found.
	 */
	User findById(int userId);

	/**
	 * Change user password
	 * 
	 * @param userId
	 *            Id of the user to change password for
	 * @param newPassword
	 *            password plaintext
	 */
	void changePassword(int userId, String newPassword);

	/**
	 * Retrieves user by email.
	 * 
	 * @param email
	 *            User's email.
	 * @return User or null if not found.
	 */
	User findByEmail(String email);

	boolean isEmailFree(String email);

	/**
	 * Deletes user with all data.
	 * 
	 * @param user
	 *            Target user.
	 */
	void deleteUser(User user);

	User registerNewUser(String email, String rawPassword) throws UserOperationException;
}
