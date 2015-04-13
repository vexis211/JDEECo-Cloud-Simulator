package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDaoImpl<T> implements BaseDao<T> {

	private final Logger logger = Logger.getLogger(BaseDaoImpl.class);

	protected SessionFactory sessionFactory;

	private Class<T> classType;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.classType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void saveOrUpdate(T item) {
		try {
			getSession().saveOrUpdate(item);
		} catch (RuntimeException re) {
			logger.error("saveOrUpdate failed", re);
			throw re;
		}
	}

	@Override
	public void merge(T item) {
		try {
			getSession().merge(item);
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void delete(T item) {
		try {
			getSession().delete(item);
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(int id) {
		try {
			// not used findUniqueByCriteria for performance purposes
			T instance = (T) getCriteria().add(Restrictions.idEq(id)).uniqueResult();
			return instance;
		} catch (RuntimeException re) {
			logger.error("findById failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findLastAdded() {
		try {
			T instance = (T) getCriteria().addOrder(Order.desc("id")).setMaxResults(1).uniqueResult();
			return instance;
		} catch (RuntimeException re) {
			logger.error("findLastAdded failed", re);
			throw re;
		}
	}

	@Override
	public List<T> findAll() {
		return findListByCriteria();
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Criteria getCriteria() {
		return getSession().createCriteria(classType);
	}

	@SuppressWarnings("unchecked")
	protected List<T> findListByCriteria(Criterion... criterion) {
		Criteria crit = getCriteria();
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	protected T findUniqueByCriteria(Criterion... criterion) {
		Criteria crit = getCriteria();
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return (T) crit.uniqueResult();
	}
}
