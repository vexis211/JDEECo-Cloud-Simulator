package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;

public interface ProjectDao extends BaseDao<Project> {

	List<Project> listVisibleProjects(User user);
}