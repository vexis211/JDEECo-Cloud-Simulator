package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;

public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDao {

	@Override
	public List<Project> listVisibleProjects(User user) {
		@SuppressWarnings("unchecked")
		List<Project> visibleProjects = (List<Project>) sessionFactory.getCurrentSession().createCriteria(Project.class)
				.createAlias("visibleForUsers", "user").add(Restrictions.eq("user.id", user.getId())).list();
		return visibleProjects;
	}
}
