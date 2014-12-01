package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.ProjectDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;

public class ProjectServiceImpl implements ProjectService {

	@Resource
	private ProjectDao projectDao;

	@Override
	public List<Project> listProjects() {
		return projectDao.findAll();
	}

	@Override
	public List<Project> listVisibleProjects() {
		//List<Project> projects = projectDao.findAll().stream().filter(p -> p.isVisible()).collect(Collectors.toList());
		// TODO implement
		List<Project> projects = listProjects();
		return projects;
	}

	@Override
	public List<Project> listHiddenProjects() {
		//List<Project> projects = projectDao.findAll().stream().filter(p -> !p.isVisible()).collect(Collectors.toList());
		// TODO implement
		List<Project> projects = new ArrayList<Project>();
		return projects;
	}

	@Transactional(readOnly = false)
	@Override
	public Project createProject(String name, String description) {
		
		User currentUser = UserHelper.getAuthenticatedUser();
		
		Project project = new Project(currentUser, name, description);
		projectDao.saveOrUpdate(project);
		
		return project;
	}

}
