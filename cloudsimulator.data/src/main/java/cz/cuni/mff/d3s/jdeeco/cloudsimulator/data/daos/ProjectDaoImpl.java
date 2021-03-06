package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;

public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDao {

	public ProjectDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<Project> listVisibleProjects(User user) {
		@SuppressWarnings("unchecked")
		List<Project> visibleProjects = (List<Project>) sessionFactory.getCurrentSession().createCriteria(Project.class)
				.createAlias("visibleForUsers", "user").add(Restrictions.eq("user.id", user.getId())).list();
		return visibleProjects;
	}
}
