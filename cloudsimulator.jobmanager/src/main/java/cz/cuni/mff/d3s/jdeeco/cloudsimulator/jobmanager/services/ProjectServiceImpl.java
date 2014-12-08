package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.ProjectDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.UserDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;

public class ProjectServiceImpl implements ProjectService {

	@Resource
	private ProjectDao projectDao;
	
	@Resource
	private UserDao userDao;

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
	

	@Override
	public Project getProjectById(int projectId) {
		return projectDao.findById(projectId);
	}
	

	@Transactional(readOnly = false)
	@Override
	public Project createProject(String name, String description) {
		
		User currentUser = UserHelper.getAuthenticatedUser();
		
		Project project = new Project(currentUser, name, description);
		projectDao.saveOrUpdate(project);
		
		return project;
	}

	@Transactional(readOnly = false)
	@Override
	public Project editProject(int projectId, String name, String description) {

		Project project = getProjectById(projectId);
		project.setName(name);
		project.setDescription(description);
		projectDao.saveOrUpdate(project);

		return project;		
	}

	@Transactional(readOnly = false)
	@Override
	public void hideProject(int projectId) {

		Project project = projectDao.findById(projectId);
		User currentUser = UserHelper.getAuthenticatedUser();
		
		currentUser.getVisibleProjects().remove(project);
		userDao.saveOrUpdate(currentUser); // TODO test
	}

}
