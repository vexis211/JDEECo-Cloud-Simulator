package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDaoImpl<T> implements BaseDao<T> {

	private final Logger logger = Logger.getLogger(BaseDaoImpl.class);

	@Resource
	protected SessionFactory sessionFactory;

	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void persist(T transientInstance) {
		try {
			getSession().persist(transientInstance);
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	@Override
	public void delete(T persistentInstance) {
		try {
			getSession().delete(persistentInstance);
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(int id) {
		logger.debug("getting SimulationConfiguration instance with id: " + id);
		try {
			// not used findUniqueByCriteria for performance purposes
			T instance = (T) getCriteria().add(Restrictions.idEq(id));
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
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
		return getSession().createCriteria(persistentClass);
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
		return (T)crit.uniqueResult();
	}

}
