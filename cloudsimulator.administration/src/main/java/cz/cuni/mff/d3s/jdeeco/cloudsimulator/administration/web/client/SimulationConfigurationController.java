package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.ProjectService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.SimulationConfigurationService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.ViewParameters;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.ProjectItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationConfigurationItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationConfigurationItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories.ProjectItemFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories.SimulationConfigurationItemFactory;

@Controller
public class SimulationConfigurationController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(SimulationConfigurationController.class);

	private static final String CONFIGURATION_VIEW = "main/configuration/simulationConfiguration";
	private static final String ADDCONFIGURATION_VIEW = "main/configuration/addSimulationConfiguration";
	private static final String EDITCONFIGURATION_VIEW = "main/configuration/editSimulationConfiguration";

	@Resource
	private ProjectService projectService;

	@Resource
	private ProjectItemFactory projectItemFactory;

	@Resource
	private SimulationConfigurationService simulationConfigurationService;

	@Resource
	private SimulationConfigurationItemFactory simulationConfigurationItemFactory;

	@Resource
	private SimulationConfigurationValidator simulationConfigurationValidator;

	@Resource
	private NavigationPathBuilder navigationPathBuilder;

	@Resource
	private AppContext appContext;

	@RequestMapping(value = MappingSettings.CONFIGURATION)
	public ModelAndView showConfiguration(HttpServletRequest request, @PathVariable int configurationId) {

		SimulationConfiguration configuration = simulationConfigurationService.getConfigurationById(configurationId);

		if (configuration != null) {
			SimulationConfigurationItem configurationItem = getConfigurationItem(configuration);

			ModelAndView modelAndView = getDefaultModelAndView(CONFIGURATION_VIEW).withSimulationConfiguration(
					configurationItem).withNavigationPath(
					navigationPathBuilder.buildFromSimulationConfiguration(configurationId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList(appContext.getSiteRoot());
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_ADD, method = RequestMethod.GET)
	public ModelAndView addConfiguration(HttpServletRequest request, @PathVariable int projectId) {

		ModelAndView modelAndView = getModelAndViewWithProject(ADDCONFIGURATION_VIEW, projectId).withCancelUri(
				MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.PROJECT_ROOT, projectId))
				.withNavigationPath(navigationPathBuilder.buildFromProject(projectId));

		return modelAndView;
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_ADD, method = RequestMethod.POST)
	public ModelAndView addConfiguration(HttpServletRequest request, @PathVariable int projectId,
			@ModelAttribute SimulationConfigurationItemImpl simulationConfigurationItem, BindingResult result) {

		simulationConfigurationValidator.validate(simulationConfigurationItem, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();

			ModelAndView modelAndView = getModelAndViewWithProject(ADDCONFIGURATION_VIEW, projectId)
					.withCancelUri(
							MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.PROJECT_ROOT,
									projectId)).withSimulationConfiguration(simulationConfigurationItem)
					.withErrorMessage(er.getDefaultMessage())
					.withNavigationPath(navigationPathBuilder.buildFromProject(projectId));

			return modelAndView;
		}

		simulationConfigurationService.createConfiguration(projectId, simulationConfigurationItem.getDataId(),
				simulationConfigurationItem.getName(), simulationConfigurationItem.getDescription(),
				simulationConfigurationItem.getDefaultRunCount());

		return ProjectController.RedirectToProject(appContext.getSiteRoot(), projectId);
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_EDIT, method = RequestMethod.GET)
	public ModelAndView editConfiguration(HttpServletRequest request, @PathVariable int configurationId) {

		SimulationConfiguration configuration = simulationConfigurationService.getConfigurationById(configurationId);

		if (configuration != null) {

			SimulationConfigurationItem configurationItem = getConfigurationItem(configuration);

			ModelAndView modelAndView = getModelAndViewWithProject(EDITCONFIGURATION_VIEW,
					configuration.getProject().getId())
					.withCancelUri(
							MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.CONFIGURATION_ROOT,
									configurationId)).withSimulationConfiguration(configurationItem)
					.withNavigationPath(navigationPathBuilder.buildFromSimulationConfiguration(configurationId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList(appContext.getSiteRoot());
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_EDIT, method = RequestMethod.POST)
	public ModelAndView editConfiguration(HttpServletRequest request, @PathVariable int configurationId,
			@ModelAttribute SimulationConfigurationItemImpl simulationConfigurationItem, BindingResult result) {

		simulationConfigurationValidator.validate(simulationConfigurationItem, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();
			SimulationConfiguration configuration = simulationConfigurationService
					.getConfigurationById(configurationId);

			ModelAndView modelAndView = getModelAndViewWithProject(EDITCONFIGURATION_VIEW,
					configuration.getProject().getId())
					.withCancelUri(
							MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.CONFIGURATION_ROOT,
									configurationId)).withSimulationConfiguration(simulationConfigurationItem)
					.withErrorMessage(er.getDefaultMessage())
					.withNavigationPath(navigationPathBuilder.buildFromSimulationConfiguration(configurationId));

			return modelAndView;
		}

		simulationConfigurationService.editConfiguration(configurationId, simulationConfigurationItem.getName(),
				simulationConfigurationItem.getDescription(), simulationConfigurationItem.getDefaultRunCount());

		return RedirectToConfiguration(configurationId);
	}

	private SimulationConfigurationItem getConfigurationItem(SimulationConfiguration configuration) {
		return simulationConfigurationItemFactory.create(configuration, true);
	}

	public ModelAndView RedirectToConfiguration(int configurationId) {
		return RedirectToConfiguration(appContext.getSiteRoot(), configurationId);
	}

	public static ModelAndView RedirectToConfiguration(String siteRoot, int configurationId) {
		return new ModelAndView("redirect:"
				+ MappingSettings.GetFullUri(siteRoot, MappingSettings.CONFIGURATION_ROOT, configurationId));
	}

	private ClientModelAndView getModelAndViewWithProject(String viewName, int projectId) {
		ClientModelAndView modelAndView = getDefaultModelAndView(viewName).withProjectId(projectId);

		// add datas
		Project project = projectService.getProjectById(projectId);
		ProjectItem projectItem = projectItemFactory.create(project, false, true);
		modelAndView.addObject(ViewParameters.SIMULATION_DATAS, projectItem.getDatas());

		return modelAndView;
	}

	private ClientModelAndView getDefaultModelAndView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
