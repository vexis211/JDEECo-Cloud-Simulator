package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.UserDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.UserInfoDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.CustomUserDetailsImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.RoleConvertor;

public class UserServiceImpl implements UserService {

	/**
	 * Logging.
	 */
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Resource
	private UserDao userDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private PasswordEncoder passwordEncoder;

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

	@Transactional(readOnly = false)
	@Override
	public final void changePassword(final int userId, final String newPassword) {
		User user = userDao.findById(userId);
		user.setPassword(passwordEncoder.encode(newPassword));
		userDao.persist(user);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteUser(User user) {
		// TODO implement
		userDao.delete(user);
	}

	/**
	 * Checks if email is not registered yet.
	 * 
	 * @param email
	 *            Email to check.
	 * @return true for not registered emails otherwise false.
	 */
	@Override
	public boolean isEmailFree(final String email) {
		return userDao.findByEmail(email) == null;
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
	@Override
	public User registerNewUser(final String email, final String rawPassword) throws UserOperationException {

		if (!isEmailFree(email)) {
			// Email is already registered.
			throw new UserOperationException(UserOperationErrorType.EMAIL_ALREADY_REGISTERED);
		}

		// Create user.
		User user = new User();
		user.setEmail(email);
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);
		user.setRole(RoleConvertor.getRole(RoleConvertor.ROLE_USER));
		userDao.setIsUserRegistered(user, true);
		userDao.persist(user);

		// Generate and store activation code.
		String activationCode = UUID.randomUUID().toString();
		userInfoDao.setActivationCode(user, activationCode);

		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByEmail(username);
		// if (user == null || !userDao.isUserActivated(user)) { // TODO
		if (user == null) {
			// No user found or not activated yet.
			throw new UsernameNotFoundException(username);
		}

		// Update last activity.
		user.setLastActivityDate(new Date());
		userDao.persist(user);
		return new CustomUserDetailsImpl(user);
	}
}
