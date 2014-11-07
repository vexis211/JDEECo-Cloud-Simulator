package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.UserInfo;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils.StringHelper;

public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements UserInfoDao {

	private static final String RESET_PASSWORD_CODE = "ResetPasswordCode";
	private static final String ACTIVATION_CODE = "ActivationCode";


	@Override
	public final String getActivationCode(final int userId) {

		UserInfo codeInfo = (UserInfo) sessionFactory.getCurrentSession().createCriteria(UserInfo.class)
				.add(Restrictions.eq("user.id", userId)).add(Restrictions.eq("name", ACTIVATION_CODE)).uniqueResult();

		if (codeInfo == null) {
			// Empty code.
			return null;
		}
		return codeInfo.getValue();
	}

	@Override
	public final void setActivationCode(final User user, final String activationCode) {

		@SuppressWarnings("unchecked")
		List<UserInfo> codeInfos = (List<UserInfo>) sessionFactory.getCurrentSession().createCriteria(UserInfo.class)
				.add(Restrictions.eq("user", user)).add(Restrictions.eq("name", ACTIVATION_CODE)).list();

		for (UserInfo codeInfo : codeInfos) {
			sessionFactory.getCurrentSession().delete(codeInfo);
		}

		if (StringHelper.isNullOrEmpty(activationCode)) {
			// Do not save empty code.
			return;
		}
		UserInfo info = new UserInfo(user, ACTIVATION_CODE, activationCode);
		sessionFactory.getCurrentSession().saveOrUpdate(info);
	}

	@Override
	public final String getResetPasswordCode(final User user) {

		UserInfo codeInfo = (UserInfo) sessionFactory.getCurrentSession().createCriteria(UserInfo.class)
				.add(Restrictions.eq("user", user)).add(Restrictions.eq("name", RESET_PASSWORD_CODE)).uniqueResult();

		if (codeInfo == null) {
			// Empty code.
			return null;
		}
		return codeInfo.getValue();
	}

	@Override
	public final void setResetPasswordCode(final User user, final String resetCode) {

		@SuppressWarnings("unchecked")
		List<UserInfo> codeInfos = (List<UserInfo>) sessionFactory.getCurrentSession().createCriteria(UserInfo.class)
				.add(Restrictions.eq("user", user)).add(Restrictions.eq("name", RESET_PASSWORD_CODE)).list();

		for (UserInfo codeInfo : codeInfos) {
			sessionFactory.getCurrentSession().delete(codeInfo);
		}

		if (StringHelper.isNullOrEmpty(resetCode)) {
			// Do not store empty code.
			return;
		}

		UserInfo info = new UserInfo(user, RESET_PASSWORD_CODE, resetCode);
		sessionFactory.getCurrentSession().saveOrUpdate(info);
	}

	@Override
	public final User findByActivationCode(final String activationCode) {
		UserInfo userInfo = (UserInfo) sessionFactory.getCurrentSession().createCriteria(UserInfo.class)
				.add(Restrictions.eq("name", ACTIVATION_CODE)).add(Restrictions.eq("value", activationCode))
				.createAlias("user", "user").uniqueResult();
		if (userInfo == null) {
			// UserInfo with requested activation code was not found.
			return null;
		}
		return userInfo.getUser();
	}

	@Override
	public final User findByResetPasswordCode(final String resetPasswordCode) {
		UserInfo userInfo = (UserInfo) sessionFactory.getCurrentSession().createCriteria(UserInfo.class)
				.add(Restrictions.eq("name", RESET_PASSWORD_CODE)).add(Restrictions.eq("value", resetPasswordCode))
				.createAlias("user", "user").uniqueResult();
		if (userInfo == null) {
			// UserInfo with requested activation code was not found.
			return null;
		}
		return userInfo.getUser();
	}
}
