package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.daos.AuthenticationTokenDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.daos.UserDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.AuthenticationToken;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.UserOperationErrorType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.UserOperationException;

/**
 * Implementation of authentication token service using daos.
 */
@Transactional(readOnly = false)
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {

	/**
	 * Default expiry in seconds from now.
	 */
	private long defaultExpiryDeltaInSeconds;

	@Resource
	UserDao userDao;

	@Autowired
	AuthenticationTokenDao authTokenDao;

	@Override
	public final AuthenticationToken createToken(final int userId, final long expireInSeconds)
			throws UserOperationException {
		User user = userDao.findById(userId);
		if (user == null) {
			throw new UserOperationException(UserOperationErrorType.NOT_FOUND);
		}
		if (expireInSeconds < 0) {
			throw new UserOperationException(UserOperationErrorType.TOKEN_EXPIRY_IS_INVALID);
		}
		String value = UUID.randomUUID().toString();
		Date created = new Date();
		Date expire = new Date(created.getTime() + expireInSeconds);
		AuthenticationToken authToken = new AuthenticationToken(user, value, created, expire);
		authTokenDao.saveOrUpdate(authToken);
		return authToken;
	}

	@Override
	public final AuthenticationToken createDefaultToken(final int userId) throws UserOperationException {
		return createToken(userId, getDefaultExpiryDeltaInSeconds());
	}

	@Override
	public final AuthenticationToken findByValue(final String tokenValue) {
		return authTokenDao.findByValue(tokenValue);
	}

	public final long getDefaultExpiryDeltaInSeconds() {
		return defaultExpiryDeltaInSeconds;
	}

	public final void setDefaultExpiryDeltaInSeconds(long defaultExpiryDeltaInSeconds) {
		this.defaultExpiryDeltaInSeconds = defaultExpiryDeltaInSeconds;
	}
}
