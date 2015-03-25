package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.User;

public interface UserService extends UserDetailsService {

	/**
	 * Registers user with email and password. Prepares account and set is as not activated.
	 * 
	 * @param email
	 *            Email to register.
	 * @param password
	 *            User raw password.
	 * @return Registered user instance.
	 * @throws UserOperationException
	 *             Throws on register error.
	 */
	User registerUser(String email, String password) throws UserOperationException;

	/**
	 * Sends activation email. If user is not activated and email is not correctly sent, user is deleted.
	 * 
	 * @param user
	 *            user to be activated
	 * @return true if mail has been sent, otherwise false
	 */
	boolean sendActivationEmail(User user);

	/**
	 * Authenticates user by activation code. On success user is activated.
	 * 
	 * @param request
	 *            HTTP request.
	 * @param activationCode
	 *            Code.
	 * @return User or null if authentication failed.
	 */
	User activateUser(HttpServletRequest request, String activationCode);

	/**
	 * Generates new reset password code for user and informs user by email.
	 * 
	 * @param email
	 *            User's email.
	 * @return User for email or null if not found.
	 */
	User requestResetPassword(String email);

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
	 * Deletes user with all data.
	 * 
	 * @param user
	 *            Target user.
	 * @throws ItemOperationException
	 */
	void deleteUser(User user) throws UserOperationException;

	/**
	 * Authenticate user by reset password code and removes code. If user has not been activated it is done.
	 * 
	 * @param request
	 *            HTTP request.
	 * @param resetPasswordCode
	 *            Code.
	 * @return User or null if authentication failed.
	 */
	User resetPasswordLogin(HttpServletRequest request, String resetPasswordCode);

	/**
	 * Login user with code. Retrieves user on success or null if failed.
	 * 
	 * @param request
	 *            HTTP request.
	 * @param anonymousLoginCode
	 *            Anonymous login code for user.
	 * @return User or null.
	 */
	User anonymousLogin(HttpServletRequest request, String anonymousLoginCode);

	/**
	 * Authenticates request with token value and returns associated user.
	 * 
	 * @param request
	 *            Request to authenticate.
	 * @param tokenValue
	 *            Token to authenticate with.
	 * @return Authenticated user.
	 * @throws UserOperationException
	 *             Thrown on error.
	 */
	User authTokenLogin(HttpServletRequest request, String tokenValue) throws UserOperationException;

	/**
	 * Manual login the user into the system
	 * 
	 * Default way for login is using the Spring security
	 * 
	 * @param request
	 *            Request to create login session for
	 * @param email
	 *            User email
	 * @param password
	 *            user password
	 * @throws UserOperationException
	 */
	void manualLogin(HttpServletRequest request, String email, String password) throws UserOperationException;

	/**
	 * Logout user manually
	 * 
	 * Logout current user.
	 * 
	 */
	void manualLogout();

	/**
	 * Retrieves user by id.
	 * 
	 * @param userId
	 *            User's id.
	 * @return User or null if not found.
	 */
	User findById(int userId);

	/**
	 * Retrieves user by email.
	 * 
	 * @param email
	 *            User's email.
	 * @return User or null if not found.
	 */
	User findByEmail(String email);

	/**
	 * Checks if email is not registered yet.
	 * 
	 * @param email
	 *            Email to check.
	 * @return true for not registered emails otherwise false.
	 */
	boolean isEmailFree(String email);

	/**
	 * Retrieves user activation status. Not updates fresh user from storage, just uses provided user.
	 * 
	 * @param user
	 *            User.
	 * @return True if is activated otherwise false.
	 */
	boolean isActivated(User user);

	/**
	 * Check whenever the supplied user is administrator
	 * 
	 * @param user
	 *            User to check
	 * @return True if administrator, false otherwise
	 */
	boolean isAdmin(User user);
}