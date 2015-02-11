package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.ProjectDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.UserDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectVisibilitySettings;

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
		User currentUser = UserHelper.getAuthenticatedUser();

		List<Project> visibleProjects = projectDao.listVisibleProjects(currentUser);

		return visibleProjects;
	}

	@Override
	public List<Project> listHiddenProjects() {
		List<Project> projects = projectDao.findAll();

		List<Project> visibleProjects = listVisibleProjects();
		projects.removeAll(visibleProjects);

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

		Set<Project> visibleProjects = new HashSet<>(listVisibleProjects());
		visibleProjects.add(project);
		updateVisibleProjects(currentUser, visibleProjects);
		
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

		Set<Project> visibleProjects = new HashSet<>(listVisibleProjects());
		visibleProjects.remove(project);

		updateVisibleProjects(currentUser, visibleProjects);
	}

	@Transactional(readOnly = false)
	@Override
	public void editProjectsVisibility(List<ProjectVisibilitySettings> visibilitySettings) {
		User currentUser = UserHelper.getAuthenticatedUser();
		Set<Project> visibleProjects = new HashSet<>(projectDao.listVisibleProjects(currentUser));

		for (ProjectVisibilitySettings projectVisibilitySettings : visibilitySettings) {
			Project project = getProjectById(projectVisibilitySettings.getProjectId());

			if (projectVisibilitySettings.getVisible()) {
				if (!visibleProjects.contains(project)) {
					visibleProjects.add(project);
				}
			} else {
				visibleProjects.remove(project);
			}
		}

		updateVisibleProjects(currentUser, visibleProjects);
	}

	private void updateVisibleProjects(User user, Set<Project> visibleProjects) {
		user.setVisibleProjects(visibleProjects);

		userDao.merge(user);
	}
}
