package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.UserActivationFlags;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.Role;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.RoleConvertor;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User findByEmail(String email) {
		return findUniqueByCriteria(Restrictions.eq("email", email));
	}

	public boolean isUserActivated(User user) {
		return UserActivationFlags.hasFlags(user, UserActivationFlags.IS_ACTIVATED);
	}

	public void setIsUserActivated(User user, boolean isActivated) {
		if (isActivated) {
			UserActivationFlags.addFlags(user, UserActivationFlags.IS_ACTIVATED);
		} else {
			UserActivationFlags.removeFlags(user, UserActivationFlags.IS_ACTIVATED);
		}
	}

	@Override
	public boolean isUserRegistered(User user) {
		boolean isRegistered = UserActivationFlags.hasFlags(user, UserActivationFlags.IS_REGISTERED);
		return isRegistered;
	}

	@Override
	public void setIsUserRegistered(User user, boolean isRegistered) {
		if (isRegistered) {
			UserActivationFlags.addFlags(user, UserActivationFlags.IS_REGISTERED);
		} else {
			UserActivationFlags.removeFlags(user, UserActivationFlags.IS_REGISTERED);
		}
	}

	/**
	 * Determine whenever the user is admin
	 * 
	 * @param user
	 *            The user to be checked
	 * @return true when admin, false otherwise
	 * 
	 */
	@Override
	public boolean isUserAdmin(User user) {
		return RoleConvertor.getRole(user.getRole()) == Role.ROLE_ADMINISTRATOR;
	}
}
