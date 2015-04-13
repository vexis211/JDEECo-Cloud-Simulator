package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.AuthenticationToken;

/**
 * Hibernate implementation of AuthTokenDao.
 */
public class AuthenticationTokenDaoImpl extends BaseDaoImpl<AuthenticationToken> implements AuthenticationTokenDao {

	public AuthenticationTokenDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * Warning: method will crash with HibernateException if there is multiple values matching.
	 */
	@Override
	public final AuthenticationToken findByValue(final String value) {
		return (AuthenticationToken) sessionFactory.getCurrentSession().createCriteria(AuthenticationToken.class)
				.add(Restrictions.eq("value", value)).uniqueResult();
	}
}
