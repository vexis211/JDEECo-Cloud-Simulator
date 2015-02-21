package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

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

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.SimulationConfigurationService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationConfigurationItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationConfigurationItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories.SimulationConfigurationItemFactory;

@Controller
public class SimulationConfigurationController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(SimulationConfigurationController.class);

	private static final String CONFIGURATION_VIEW = "main/configuration/simulationConfiguration";
	private static final String ADDCONFIGURATION_VIEW = "main/configuration/addSimulationConfiguration";
	private static final String EDITCONFIGURATION_VIEW = "main/configuration/editSimulationConfiguration";

	@Resource
	private SimulationConfigurationService simulationConfigurationService;

	@Resource
	private SimulationConfigurationItemFactory simulationConfigurationItemFactory;

	@Resource
	private SimulationConfigurationValidator simulationConfigurationValidator;

	@Resource
	private NavigationPathBuilder navigationPathBuilder;

	@RequestMapping(value = MappingSettings.CONFIGURATION)
	public ModelAndView showConfiguration(HttpServletRequest request, @PathVariable int configurationId) {

		SimulationConfiguration configuration = simulationConfigurationService.getConfigurationById(configurationId);

		if (configuration != null) {
			SimulationConfigurationItem configurationItem = getConfigurationItem(configuration);

			ModelAndView modelAndView = getDefaultModelAnView(CONFIGURATION_VIEW).withSimulationConfiguration(
					configurationItem)
					.withNavigationPath(navigationPathBuilder.buildFromSimulationConfiguration(configurationId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_ADD, method = RequestMethod.GET)
	public ModelAndView addConfiguration(HttpServletRequest request, @PathVariable int projectId) {

		ModelAndView modelAndView = getDefaultModelAnView(ADDCONFIGURATION_VIEW).withCancelUri(
				String.format("%s/%s", MappingSettings.PROJECT_ROOT, projectId)).withProjectId(projectId)
				.withNavigationPath(navigationPathBuilder.buildFromProject(projectId));

		return modelAndView;
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_ADD, method = RequestMethod.POST)
	public ModelAndView addConfiguration(HttpServletRequest request, @PathVariable int projectId,
			@ModelAttribute SimulationConfigurationItemImpl simulationConfigurationItem, BindingResult result) {

		simulationConfigurationValidator.validate(simulationConfigurationItem, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();

			ModelAndView modelAndView = getDefaultModelAnView(ADDCONFIGURATION_VIEW)
					.withCancelUri(String.format("%s/%s", MappingSettings.PROJECT_ROOT, projectId))
					.withProjectId(projectId).withSimulationConfiguration(simulationConfigurationItem)
					.withErrorMessage(er.getDefaultMessage())
					.withNavigationPath(navigationPathBuilder.buildFromProject(projectId));

			return modelAndView;
		}

		simulationConfigurationService.createConfiguration(projectId, simulationConfigurationItem.getName(),
				simulationConfigurationItem.getDescription());

		return ProjectController.RedirectToProject(projectId);
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_EDIT, method = RequestMethod.GET)
	public ModelAndView editConfiguration(HttpServletRequest request, @PathVariable int configurationId) {

		SimulationConfiguration configuration = simulationConfigurationService.getConfigurationById(configurationId);

		if (configuration != null) {

			SimulationConfigurationItem configurationItem = getConfigurationItem(configuration);

			ModelAndView modelAndView = getDefaultModelAnView(EDITCONFIGURATION_VIEW).withCancelUri(
					String.format("%s/%s", MappingSettings.CONFIGURATION_ROOT, configurationId))
					.withSimulationConfiguration(configurationItem)
					.withNavigationPath(navigationPathBuilder.buildFromSimulationConfiguration(configurationId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_EDIT, method = RequestMethod.POST)
	public ModelAndView editConfiguration(HttpServletRequest request, @PathVariable int configurationId,
			@ModelAttribute SimulationConfigurationItemImpl simulationConfigurationItem, BindingResult result) {

		simulationConfigurationValidator.validate(simulationConfigurationItem, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();

			ModelAndView modelAndView = getDefaultModelAnView(EDITCONFIGURATION_VIEW)
					.withCancelUri(String.format("%s/%s", MappingSettings.CONFIGURATION_ROOT, configurationId))
					.withSimulationConfiguration(simulationConfigurationItem).withErrorMessage(er.getDefaultMessage())
					.withNavigationPath(navigationPathBuilder.buildFromSimulationConfiguration(configurationId));

			return modelAndView;
		}

		simulationConfigurationService.editConfiguration(configurationId, simulationConfigurationItem.getName(),
				simulationConfigurationItem.getDescription());

		return RedirectToConfiguration(configurationId);
	}

	private SimulationConfigurationItem getConfigurationItem(SimulationConfiguration configuration) {
		return simulationConfigurationItemFactory.create(configuration, true);
	}

	public static ModelAndView RedirectToConfiguration(int configurationId) {

		return new ModelAndView(String.format("redirect:%s/%s", MappingSettings.CONFIGURATION_ROOT, configurationId));
	}

	private ClientModelAndView getDefaultModelAnView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
