package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class HibernateHelper {

	public static Session getSession(SessionFactory sessionFactory) {
		Session session = sessionFactory.getCurrentSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));

		session.setFlushMode(FlushMode.ALWAYS);
		return session;
	}

	public static void closeSession(SessionFactory sessionFactory, Session session) {
		if (session.isOpen() && session.isConnected()) {
			session.flush();
			session.clear();
			session.close();
			TransactionSynchronizationManager.unbindResource(sessionFactory);
		}
	}
}
