package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

// Generated Oct 16, 2014 10:41:33 PM by Hibernate Tools 4.0.0

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationRun;

/**
 * Home object for domain model class SimulationRun.
 * @see cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationRun
 * @author Hibernate Tools
 */
public class SimulationRunHome {

	private static final Log log = LogFactory.getLog(SimulationRunHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(SimulationRun transientInstance) {
		log.debug("persisting SimulationRun instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SimulationRun instance) {
		log.debug("attaching dirty SimulationRun instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SimulationRun instance) {
		log.debug("attaching clean SimulationRun instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SimulationRun persistentInstance) {
		log.debug("deleting SimulationRun instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SimulationRun merge(SimulationRun detachedInstance) {
		log.debug("merging SimulationRun instance");
		try {
			SimulationRun result = (SimulationRun) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SimulationRun findById(java.lang.Integer id) {
		log.debug("getting SimulationRun instance with id: " + id);
		try {
			SimulationRun instance = (SimulationRun) sessionFactory
					.getCurrentSession()
					.get("cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationRun",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SimulationRun instance) {
		log.debug("finding SimulationRun instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationRun")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
