package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import java.util.List;
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

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.ProjectService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.ViewParameters;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories.ProjectItemFactory;

@Controller
public class ProjectController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ProjectController.class);

	private static final String PROJECTLIST_VIEW = "main/project/projectList";
	private static final String PROJECT_VIEW = "main/project/project";
	private static final String CREATEPROJECT_VIEW = "main/project/createProject";
	private static final String EDITPROJECT_VIEW = "main/project/editProject";
	
	@Resource
	private ProjectService projectService;

	@Resource
	private ProjectItemFactory projectItemFactory;

	@Resource
	private ProjectValidator projectValidator;

	
	@RequestMapping(value = MappingSettings.MAIN)
	public ModelAndView showProjectList(HttpServletRequest request) {

		ModelAndView modelAndView = ClientHelper.getDefaultModel(PROJECTLIST_VIEW);

		List<Project> projects = projectService.listVisibleProjects();
		List<ProjectItem> projectItems = getProjectItems(projects);
		modelAndView.addObject(ViewParameters.PROJECTS, projectItems);

		return modelAndView;
	}

	@RequestMapping(value = MappingSettings.PROJECT)
	public ModelAndView showProject(HttpServletRequest request, @PathVariable int projectId) {

		Project project = projectService.getProjectById(projectId);
		
		if (project != null) {		
			ModelAndView modelAndView = ClientHelper.getDefaultModel(PROJECT_VIEW);
			ProjectItem projectItem = getProjectItem(project);
			modelAndView.addObject(ViewParameters.PROJECT, projectItem);
						
			return modelAndView;
		}
		else return RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.PROJECT_CREATE, method = RequestMethod.GET)
	public ModelAndView createProject(HttpServletRequest request) {

		return ClientHelper.getDefaultModel(CREATEPROJECT_VIEW);
	}
	
	@RequestMapping(value = MappingSettings.PROJECT_CREATE, method = RequestMethod.POST)
	public ModelAndView createProject(HttpServletRequest request, @ModelAttribute ProjectItemImpl projectItem, BindingResult result) {
			
		projectValidator.validate(projectItem, result);
		if (result.hasErrors()) {
			ModelAndView modelAndView = ClientHelper.getDefaultModel(CREATEPROJECT_VIEW);
			modelAndView.addObject(ViewParameters.MODEL_VAR, projectItem);
			FieldError er = result.getFieldError();
			modelAndView.addObject(ViewParameters.ERROR_MSG_VAR, er.getDefaultMessage());
			return modelAndView;
		}
		
		projectService.createProject(projectItem.getName(), projectItem.getDescription());
		
		return RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.PROJECT_EDIT)
	public ModelAndView editProject(HttpServletRequest request, @PathVariable int projectId) {

		Project project = projectService.getProjectById(projectId);
		
		if (project != null) {		
			ModelAndView modelAndView = ClientHelper.getDefaultModel(EDITPROJECT_VIEW);
			ProjectItem projectItem = getProjectItem(project);
			modelAndView.addObject(ViewParameters.PROJECT, projectItem);
						
			return modelAndView;
		}
		else return RedirectToProjectList();
	}
	
	@RequestMapping(value = MappingSettings.PROJECT_EDIT, method = RequestMethod.POST)
	public ModelAndView editProject(HttpServletRequest request, @PathVariable int projectId,
			@ModelAttribute ProjectItemImpl projectItem, BindingResult result) {
			
		projectValidator.validate(projectItem, result);
		if (result.hasErrors()) {
			ModelAndView modelAndView = ClientHelper.getDefaultModel(CREATEPROJECT_VIEW);
			modelAndView.addObject(ViewParameters.MODEL_VAR, projectItem);
			FieldError er = result.getFieldError();
			modelAndView.addObject(ViewParameters.ERROR_MSG_VAR, er.getDefaultMessage());
			return modelAndView;
		}
		
		projectService.editProject(projectId, projectItem.getName(), projectItem.getDescription());

		return RedirectToProject(projectId);
	}

	
	@RequestMapping(value = MappingSettings.PROJECT_HIDE)
	public ModelAndView hideProject(HttpServletRequest request, @PathVariable int projectId) {

		projectService.hideProject(projectId);
		
		return RedirectToProjectList();
	}

	public static ModelAndView RedirectToProject(int projectId) {
		
		return ClientHelper.getDefaultModel(String.format("redirect:%s/%s", MappingSettings.PROJECT_ROOT, projectId));
	}
	
	public static ModelAndView RedirectToProjectList() {
		return ClientHelper.getDefaultModel("redirect:" + MappingSettings.MAIN);
	}
	
	private List<ProjectItem> getProjectItems(List<Project> projects) {

		List<ProjectItem> projectItems = projects.stream()
				.sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName())).map(p -> getProjectItem(p))
				.collect(Collectors.toList());

		return projectItems;
	}

	private ProjectItem getProjectItem(Project project) {
		ProjectItem projectItem = projectItemFactory.create(project, true);

		return projectItem;
	}
}
