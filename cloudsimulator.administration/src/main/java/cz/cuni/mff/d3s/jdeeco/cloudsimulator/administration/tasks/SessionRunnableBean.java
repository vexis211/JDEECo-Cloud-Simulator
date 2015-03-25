package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.tasks;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.utils.HibernateHelper;

public abstract class SessionRunnableBean implements Runnable {

	
	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public final void run() {
		Session session = HibernateHelper.getSession(sessionFactory);
		try {
			runInSession();
		}
		finally {
			HibernateHelper.closeSession(sessionFactory, session);
		}
	}
	
	protected abstract void runInSession();
}
