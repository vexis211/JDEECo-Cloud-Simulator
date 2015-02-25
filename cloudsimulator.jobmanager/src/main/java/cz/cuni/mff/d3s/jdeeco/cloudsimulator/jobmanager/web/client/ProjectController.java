package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.ProjectService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectListVisibilitySettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectVisibilitySettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories.ProjectItemFactory;

@Controller
public class ProjectController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ProjectController.class);

	private static final String PROJECTLIST_VIEW = "main/project/projectList";
	private static final String PROJECT_VIEW = "main/project/project";
	private static final String ADDPROJECT_VIEW = "main/project/addProject";
	private static final String EDITPROJECT_VIEW = "main/project/editProject";
	private static final String CONFIGUREPROJECTSVISIBILITY_VIEW = "main/project/configureProjectsVisibility";

	@Resource
	private ProjectService projectService;

	@Resource
	private ProjectItemFactory projectItemFactory;

	@Resource
	private ProjectValidator projectValidator;

	@Resource
	private NavigationPathBuilder navigationPathBuilder;

	@Resource
	private AppContext appContext;

	@RequestMapping(value = MappingSettings.MAIN)
	public ModelAndView showProjectList(HttpServletRequest request) {

		List<Project> projects = projectService.listVisibleProjects();
		List<ProjectItem> projectItems = getProjectItems(projects);

		ModelAndView modelAndView = getDefaultModelAnView(PROJECTLIST_VIEW).withProjects(projectItems)
				.withNavigationPath(navigationPathBuilder.buildFromHome());

		return modelAndView;
	}

	@RequestMapping(value = MappingSettings.PROJECT)
	public ModelAndView showProject(HttpServletRequest request, @PathVariable int projectId) {

		Project project = projectService.getProjectById(projectId);

		if (project != null) {
			ProjectItem projectItem = projectItemFactory.create(project, true, true);

			ModelAndView modelAndView = getDefaultModelAnView(PROJECT_VIEW).withProject(projectItem)
					.withNavigationPath(navigationPathBuilder.buildFromProject(projectId));

			return modelAndView;
		}

		return RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.PROJECT_ADD, method = RequestMethod.GET)
	public ModelAndView addProject(HttpServletRequest request) {

		return getDefaultModelAnView(ADDPROJECT_VIEW).withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.MAIN));
	}

	@RequestMapping(value = MappingSettings.PROJECT_ADD, method = RequestMethod.POST)
	public ModelAndView addProject(HttpServletRequest request, @ModelAttribute ProjectItemImpl projectItem,
			BindingResult result) {

		projectValidator.validate(projectItem, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();

			ModelAndView modelAndView = getDefaultModelAnView(ADDPROJECT_VIEW)
					.withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.MAIN))
					.withProject(projectItem).withErrorMessage(er.getDefaultMessage())
					.withNavigationPath(navigationPathBuilder.buildFromHome());

			return modelAndView;
		}

		projectService.createProject(projectItem.getName(), projectItem.getDescription());

		return RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.PROJECT_EDIT)
	public ModelAndView editProject(HttpServletRequest request, @PathVariable int projectId) {

		Project project = projectService.getProjectById(projectId);

		if (project != null) {
			ProjectItem projectItem = projectItemFactory.create(project, false, false);

			ModelAndView modelAndView = getDefaultModelAnView(EDITPROJECT_VIEW)
					.withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.PROJECT_ROOT, projectId))
					.withProject(projectItem).withNavigationPath(navigationPathBuilder.buildFromProject(projectId));

			return modelAndView;
		}

		return RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.PROJECT_EDIT, method = RequestMethod.POST)
	public ModelAndView editProject(HttpServletRequest request, @PathVariable int projectId,
			@ModelAttribute ProjectItemImpl projectItem, BindingResult result) {

		projectValidator.validate(projectItem, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();

			ModelAndView modelAndView = getDefaultModelAnView(EDITPROJECT_VIEW)
					.withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.PROJECT_ROOT, projectId))
					.withProject(projectItem).withErrorMessage(er.getDefaultMessage())
					.withNavigationPath(navigationPathBuilder.buildFromProject(projectId));

			return modelAndView;
		}

		projectService.editProject(projectId, projectItem.getName(), projectItem.getDescription());

		return RedirectToProject(projectId);
	}

	@RequestMapping(value = MappingSettings.PROJECT_CONFIGUREVISIBILITY)
	public ModelAndView configureVisibility(HttpServletRequest request) {

		Set<Project> visibleProjects = new HashSet<Project>(projectService.listVisibleProjects());

		List<ProjectVisibilitySettings> visibilitySettings = new ArrayList<>();
		for (Project project : projectService.listProjects()) {
			visibilitySettings.add(new ProjectVisibilitySettings(project.getId(), project.getName(), visibleProjects
					.contains(project)));
		}

		ModelAndView modelAndView = getDefaultModelAnView(CONFIGUREPROJECTSVISIBILITY_VIEW)
				.withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.MAIN))
				.withProjectListVisibility(new ProjectListVisibilitySettings(visibilitySettings))
				.withNavigationPath(navigationPathBuilder.buildFromHome());

		return modelAndView;
	}

	@RequestMapping(value = MappingSettings.PROJECT_CONFIGUREVISIBILITY, method = RequestMethod.POST)
	public ModelAndView configureVisibility(HttpServletRequest request,
			@ModelAttribute ProjectListVisibilitySettings projectListVisibilitySettings, BindingResult result) {

		projectService.editProjectsVisibility(projectListVisibilitySettings.getVisibilitySettings());

		return RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.PROJECT_HIDE)
	public ModelAndView hideProject(HttpServletRequest request, @PathVariable int projectId) {

		projectService.hideProject(projectId);

		return RedirectToProjectList();
	}

	private ModelAndView RedirectToProject(int projectId){
		return RedirectToProject(appContext.getSiteRoot(), projectId);
	}
	
	public static ModelAndView RedirectToProject(String siteRoot, int projectId) {
		return new ModelAndView("redirect:" + MappingSettings.GetFullUri(siteRoot, MappingSettings.PROJECT_ROOT, projectId));
	}

	private ModelAndView RedirectToProjectList(){
		return RedirectToProjectList(appContext.getSiteRoot());
	}
	
	public static ModelAndView RedirectToProjectList(String siteRoot) {
		return new ModelAndView("redirect:" + MappingSettings.GetFullUri(siteRoot, MappingSettings.MAIN));
	}

	private List<ProjectItem> getProjectItems(List<Project> projects) {

		List<ProjectItem> projectItems = projects.stream()
				.sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
				.map(p -> projectItemFactory.create(p, true, false)).collect(Collectors.toList());

		return projectItems;
	}

	private ClientModelAndView getDefaultModelAnView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
