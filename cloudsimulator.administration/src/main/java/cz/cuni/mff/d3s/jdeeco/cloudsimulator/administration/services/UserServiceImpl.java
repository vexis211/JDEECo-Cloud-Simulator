package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.UserDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.UserInfoDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.AuthenticationCodeType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.CodeAuthenticationToken;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.CustomUserDetailsImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.SecurityService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.UserHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.LoginCodeInfo;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.Role;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.RoleConvertor;

public class UserServiceImpl implements UserService, SecurityService {

	/**
	 * Logging.
	 */
	private final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Resource
	private UserDao userDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private EmailService emailService;

	@Resource
	private AuthenticationManager authenticationManager;

	/**
	 * Spring security login with credentials.
	 */
	@Transactional(readOnly = false)
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		User user = userDao.findByEmail(username);
		if (user == null) {
			// No user found.
			throw new UsernameNotFoundException(username);
		}
		else if (userDao.isUserActivated(user)) {
			// Update last activity.
			user.setLastActivityDate(new Date());
			userDao.saveOrUpdate(user);
		}
		else {
			// User not activated yet.
			sendActivationEmail(user);
		}

		return new CustomUserDetailsImpl(user);
	}

	/**
	 * Updates password and generates activation code for already registered but not activated user.
	 * 
	 * @param user
	 * @param rawPassword
	 */
	private void registerUserAgain(User user, String rawPassword) throws UserOperationException {
		if (userDao.isUserActivated(user)) {
			// Cannot register already activated user.
			throw new UserOperationException(UserOperationErrorType.EMAIL_ALREADY_REGISTERED);
		}
		String activationCode = UUID.randomUUID().toString();
		userInfoDao.setActivationCode(user, activationCode);
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);
		userDao.saveOrUpdate(user);
	}

	/**
	 * Creates user with registered attribute and provided password. User is not activated yet but has generated
	 * activationCode and can be activated.
	 * 
	 * @param email
	 *            New user's unique email.
	 * @param rawPassword
	 *            New user's password in plain text.
	 * @throws UserOperationException
	 *             Throws on register error.
	 * @return User.
	 */
	@Transactional(readOnly = false)
	private User registerNewUser(final String email, final String rawPassword) throws UserOperationException {

		if (!isEmailFree(email)) {
			// Email is already registered.
			throw new UserOperationException(UserOperationErrorType.EMAIL_ALREADY_REGISTERED);
		}

		// Create user.
		User user = new User();
		user.setEmail(email);
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);
		user.setRole(RoleConvertor.getRole(Role.ROLE_USER));
		userDao.setIsUserRegistered(user, true);
		userDao.saveOrUpdate(user);

		// Generate and store activation code.
		String activationCode = UUID.randomUUID().toString();
		userInfoDao.setActivationCode(user, activationCode);

		return user;
	}

	/**
	 * Registers users and sends activation email. For already registered or generated but not activated users will
	 * generate new activation code and email. Otherwise should throw a runtime exception with reason.
	 * 
	 * @param email
	 *            Email of user.
	 * @param password
	 *            Password of user.
	 * @return User model which registration apply to.
	 * @throws UserOperationException
	 *             Throws on register error.
	 */
	@Transactional(readOnly = false)
	@Override
	public final User registerUser(final String email, final String password) throws UserOperationException {
		User user = userDao.findByEmail(email);
		if (user != null) {
			// There is some user with email already.
			registerUserAgain(user, password);
		} else {
			// No user found, register new.
			user = registerNewUser(email, password);
		}

		if (user == null) {
			// User was not registered due to uknown error.
			logger.warn(String.format("Cannot register user with email %s, due to unknown errors.", email));
			return null;
		}

		if (!sendActivationEmail(user)) {
			return null;
		}

		return user;
	}

	@Override
	public boolean sendActivationEmail(User user) {

		if (userDao.isUserActivated(user)) {
			logger.warn("User is already activated, email has not been sent.");
			return false;
		}

		String activationCode = userInfoDao.getActivationCode(user.getId());

		if (activationCode == null) {
			logger.warn("Activation code was not here. New activation code has been generated.");
			activationCode = UUID.randomUUID().toString();
			userInfoDao.setActivationCode(user, activationCode);
		}

		try {
			emailService.sendActivationEmail(user, activationCode);
		} catch (MailException ex) {
			// Probably email sending error.
			logger.error(String.format("Activation email failed deleting user %s.", user.getEmail()), ex);
			userDao.delete(user);
			return false;
		} catch (Exception ex) {
			// Probably email rendering error.
			logger.error(String.format("Activation email failed deleting user %s.", user.getEmail()), ex);
			userDao.delete(user);
			return false;
		}
		return true;
	}

	@Transactional(readOnly = false)
	@Override
	public final User activateUser(final HttpServletRequest request, final String activationCode) {
		try {
			// Try login some use with provided anonymous login code.
			authenticateUserWithCode(request, AuthenticationCodeType.ACTIVATION, activationCode);

			// Gets logged user.
			User user = UserHelper.getAuthenticatedUser();
			if (user == null) {
				// User was not authorized.
				return null;
			}

			activateUserCore(user);

			return user;
		} catch (RuntimeException e) {
			// Error happened during trying to authorize user.
			logger.error("Error during activating user", e);
			throw e;
		}
	}

	/**
	 * Activate user
	 * 
	 * @param user
	 *            User to activate
	 */
	private void activateUserCore(User user) {
		// Set user as activated.
		userDao.setIsUserActivated(user, true);
		userDao.saveOrUpdate(user);

		// Remove code because it should work only once.
		userInfoDao.setActivationCode(user, null);
	}

	@Transactional(readOnly = false)
	@Override
	public User requestResetPassword(String email) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			// User not found.
			return null;
		}
		try {
			// Generate and set reset password code for user.
			String resetPasswordCode = UUID.randomUUID().toString();
			userInfoDao.setResetPasswordCode(user, resetPasswordCode);
			emailService.sendResetPasswordEmail(user, resetPasswordCode);
			userDao.saveOrUpdate(user);
			return user;
		} catch (MailException ex) {
			logger.error(String.format("Cannot reset password for user '%s'", email), ex);
			return null;
		} catch (Exception ex) {
			logger.error(String.format("Cannot reset password for user '%s'", email), ex);
			return null;
		}
	}

	@Transactional(readOnly = false)
	@Override
	public final User resetPasswordLogin(final HttpServletRequest request, final String resetPasswordCode) {
		try {
			// Try login some use with provided anonymous login code.
			authenticateUserWithCode(request, AuthenticationCodeType.RESET_PASSWORD, resetPasswordCode);

			// Gets logged user.
			User user = UserHelper.getAuthenticatedUser();
			if (user == null) {
				// User was not authorized.
				return null;
			}

			// User was authorized, remove code because it should work only
			// once.
			userInfoDao.setResetPasswordCode(user, null);

			// activate user, if it has not been done yet
			if (!isActivated(user)) {
				activateUserCore(user);
			}

			return user;
		} catch (RuntimeException e) {
			// Error happened during trying to authorize user.
			logger.error("Error during reseting user's password", e);
			throw e;
		}
	}

	@Override
	public User findById(int userId) {
		User user = userDao.findById(userId);
		return user;
	}

	@Override
	public User findByEmail(String email) {
		User user = userDao.findByEmail(email);
		return user;
	}

	@Override
	public boolean isEmailFree(final String email) {
		return userDao.findByEmail(email) == null;
	}

	@Override
	public final User findByResetCode(final String resetPasswordCode) {
		return userInfoDao.findByResetPasswordCode(resetPasswordCode);
	}

	@Transactional(readOnly = false)
	@Override
	public final void changePassword(final int userId, final String newPassword) {
		User user = userDao.findById(userId);
		user.setPassword(passwordEncoder.encode(newPassword));
		userDao.saveOrUpdate(user);
	}

	/**
	 * Logs out current user.
	 */
	private void logoutCurrentUser() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	@Override
	public void manualLogin(HttpServletRequest request, String email, String password) throws UserOperationException {
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);

		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(authRequest);
		} catch (BadCredentialsException ex) {
			// Catch bad credentials.
		}

		if (authentication == null || !authentication.isAuthenticated()) {
			User user = findByEmail(email);
			if (user != null && !isActivated(user)) {
				sendActivationEmail(user);
				throw new UserOperationException(UserOperationErrorType.ACCOUNT_NOT_ACTIVATED);
			}
			throw new UserOperationException(UserOperationErrorType.NOT_FOUND);
		}
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);

		// Authenticate the user
		// Create a new session and add the security context.
		// The request is checked because of tests. Test environment do not set request parameters.
		if (request != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		}
	}

	@Override
	public final void manualLogout() {
		logoutCurrentUser();
	}

	@Transactional(readOnly = false)
	@Override
	public User anonymousLogin(HttpServletRequest request, String anonymousLoginCode) {
		try {
			// Try login some use with provided anonymous login code.
			authenticateUserWithCode(request, AuthenticationCodeType.ANONYMOUS_LOGIN, anonymousLoginCode);

			// Gets logged user.
			User user = UserHelper.getAuthenticatedUser();
			return user;
		} catch (RuntimeException e) {
			logger.error("Error during anonymous code login", e);
			throw e;
		}
	}

	/**
	 * Authenticate user using spring-security with provided code and codeType. If login fails and there is some user
	 * logged in, it will logout him. On successful login, user lastActivityDate is updated to current date.
	 * 
	 * @param request
	 *            HTTP request.
	 * @param codeType
	 *            Authentication code type.
	 * @param code
	 *            Authentication code.
	 */
	private void authenticateUserWithCode(final HttpServletRequest request, final AuthenticationCodeType codeType,
			final String code) {
		Authentication authentication = new CodeAuthenticationToken(codeType, code);
		Authentication authenticated = authenticationManager.authenticate(authentication);
		if (!authenticated.isAuthenticated()) {
			if (UserHelper.getAuthenticatedUser() != null) {
				logoutCurrentUser();
			}
			return;
		}
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authenticated);

		// Authenticate the user
		// Create a new session and add the security context.
		// This is not done when testing as tests do not have valid session
		if (request != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		}

		// Update new logged user last activity date.
		User loggedUser = UserHelper.getAuthenticatedUser();
		loggedUser.setLastActivityDate(new Date());
		userDao.saveOrUpdate(loggedUser);
	}

	@Override
	public User findByAnonymousLoginCode(String anonymousLoginCode) {
		LoginCodeInfo info = userInfoDao.findByAnonymousLoginCode(anonymousLoginCode);
		if (info == null) {
			// Code not found.
			return null;
		}
		return info.getUser();
	}

	@Override
	public final User findByActivationCode(final String activationCode) {
		return userInfoDao.findByActivationCode(activationCode);
	}

	@Override
	public final boolean isActivated(final User user) {
		boolean isActivated = userDao.isUserActivated(user);
		return isActivated;
	}

	@Override
	public final boolean isAdmin(final User user) {
		boolean isAdmin = userDao.isUserAdmin(user);
		return isAdmin;
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteUser(User user) throws UserOperationException {
		userDao.delete(user);
	}

	@Override
	public User authTokenLogin(HttpServletRequest request, String tokenValue)
		throws UserOperationException {
		try {
			// Try login some use with provided anonymous login code.
			authenticateUserWithCode(request, AuthenticationCodeType.AUTH_TOKEN, tokenValue);

			// Gets logged user.
			User user = UserHelper.getAuthenticatedUser();
			return user;
		} catch (RuntimeException e) {
			logger.error("Error during download code login", e);
			throw e;
		}
	}	
}
