package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;

public interface ProjectDao extends BaseDao<Project> {

	List<Project> listVisibleProjects(User user);
}