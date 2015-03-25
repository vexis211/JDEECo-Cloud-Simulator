package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.NullArgumentException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.StringEx;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.LoginCodeInfo;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.UserInfo;

public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements UserInfoDao {

	private static final String RESET_PASSWORD_CODE = "ResetPasswordCode";
	private static final String ACTIVATION_CODE = "ActivationCode";
	private static final String ANONYMOUS_LOGIN_CODE = "AnonymousLoginCode";
	private static final String ANONYMOUS_LOGIN_DATE = "AnonymousLoginDate";
	/**
	 * Formatting for date stored as user info.
	 */
	private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

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

		if (StringEx.isNullOrEmpty(activationCode)) {
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

		if (StringEx.isNullOrEmpty(resetCode)) {
			// Do not store empty code.
			return;
		}

		UserInfo info = new UserInfo(user, RESET_PASSWORD_CODE, resetCode);
		sessionFactory.getCurrentSession().saveOrUpdate(info);
	}

	@Override
	public final LoginCodeInfo getAnonymousLoginCode(final int userId) {
		LoginCodeInfo info = getLoginCode(userId, ANONYMOUS_LOGIN_CODE, ANONYMOUS_LOGIN_DATE);
		return info;
	}

	@Override
	public final void setAnonymousLoginCode(final LoginCodeInfo code) {
		saveLoginCode(code, ANONYMOUS_LOGIN_CODE, ANONYMOUS_LOGIN_DATE);
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

	@Override
	public final LoginCodeInfo findByAnonymousLoginCode(final String anonymousLoginCode) {
		LoginCodeInfo info = findByLoginCode(anonymousLoginCode, ANONYMOUS_LOGIN_CODE, ANONYMOUS_LOGIN_DATE);
		return info;
	}

	/**
	 * Retrieves code and date for user.
	 * 
	 * @param userId
	 *            Id of code owner.
	 * @param codeKey
	 *            Key identifier for code.
	 * @param dateKey
	 *            Key identifier for name.
	 * @return LoginCodeInfo instance or null if code not found.
	 */
	private LoginCodeInfo getLoginCode(final int userId, final String codeKey, final String dateKey) {
		@SuppressWarnings("unchecked")
		List<UserInfo> infos = (List<UserInfo>) sessionFactory.getCurrentSession().createCriteria(UserInfo.class)
				.add(Restrictions.eq("user.id", userId))
				.add(Restrictions.in("name", new String[] { codeKey, dateKey })).list();
		String code = null;
		String dateString = null;
		for (UserInfo info : infos) {
			if (info.getName().equals(codeKey)) {
				code = info.getValue();
			} else if (info.getName().equals(dateKey)) {
				dateString = info.getValue();
			}
		}
		if (code == null) {
			// No code found.
			return null;
		}

		Date date = dateFromString(dateString, null);
		LoginCodeInfo codeInfo = new LoginCodeInfo(code, date, infos.get(0).getUser());
		return codeInfo;
	}

	/**
	 * Stores code and date from structure.
	 * 
	 * @param info
	 *            Code structure to be stored.
	 * @param codeKey
	 *            Key identifier for code.
	 * @param dateKey
	 *            Key identifier for name.
	 */
	private void saveLoginCode(final LoginCodeInfo info, final String codeKey, final String dateKey) {
		deleteInfosForUser(codeKey, info.getUser().getId());
		deleteInfosForUser(dateKey, info.getUser().getId());
		if (StringEx.isNullOrEmpty(info.getCode())) {
			// Do not save empty code or date.
			return;
		}

		if (info.getCreated() == null) {
			// Set date to now if null provided.
			info.setCreated(new Date());
		}

		UserInfo codeInfo = new UserInfo(info.getUser(), codeKey, info.getCode());
		String dateString = dateToString(info.getCreated());
		UserInfo dateInfo = new UserInfo(info.getUser(), dateKey, dateString);
		sessionFactory.getCurrentSession().saveOrUpdate(codeInfo);
		sessionFactory.getCurrentSession().saveOrUpdate(dateInfo);
	}

	/**
	 * Retrieves code and date for code value.
	 * 
	 * @param codeValue
	 *            Value of code to search for.
	 * @param codeKey
	 *            Key identifier for code.
	 * @param dateKey
	 *            Key identifier for name.
	 * @return LoginCodeInfo instance or null if code not found.
	 */
	private LoginCodeInfo findByLoginCode(final String codeValue, final String codeKey, final String dateKey) {
		UserInfo info = (UserInfo) sessionFactory.getCurrentSession().createCriteria(UserInfo.class)
				.createAlias("user", "user", JoinType.INNER_JOIN).add(Restrictions.eq("name", codeKey))
				.add(Restrictions.eq("value", codeValue)).uniqueResult();
		if (info == null) {
			return null;
		}
		LoginCodeInfo resultInfo = getLoginCode(info.getUser().getId(), codeKey, dateKey);
		return resultInfo;
	}

	/**
	 * Converts string in format 'yyyy/MM/dd HH:mm:ss' to date.
	 * 
	 * @param dateString
	 *            String in 'yyyy/MM/dd HH:mm:ss'.
	 * @param dateOnError
	 *            Default date on parsing error.
	 * @return Date or dateOnError on parsing error.
	 */
	private Date dateFromString(final String dateString, final Date dateOnError) {
		if (dateString == null) {
			return dateOnError;
		}
		try {
			Date date = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(dateString);
			return date;
		} catch (ParseException ex) {
			return dateOnError;
		}
	}

	/**
	 * Converts date to string in format 'yyyy/MM/dd HH:mm:ss'.
	 * 
	 * @param date
	 *            Date to converted.
	 * @return Converted date.
	 */
	private String dateToString(final Date date) {
		if (date == null) {
			throw new NullArgumentException("date");
		}
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
		String dateString = format.format(date);
		return dateString;
	}

	/**
	 * Removes all infos with name for user with userId.
	 * 
	 * @param name
	 *            Name of info.
	 * @param userId
	 *            Id of owner of info.
	 */
	private void deleteInfosForUser(final String name, final int userId) {
		@SuppressWarnings("unchecked")
		List<UserInfo> infos = (List<UserInfo>) sessionFactory.getCurrentSession().createCriteria(UserInfo.class)
				.add(Restrictions.eq("user.id", userId)).add(Restrictions.eq("name", name)).list();
		for (UserInfo info : infos)
			sessionFactory.getCurrentSession().delete(info);
	}
}
