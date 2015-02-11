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
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.ViewParameters;
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

	@RequestMapping(value = MappingSettings.CONFIGURATION)
	public ModelAndView showConfiguration(HttpServletRequest request, @PathVariable int configurationId) {

		SimulationConfiguration configuration = simulationConfigurationService.getConfigurationById(configurationId);

		if (configuration != null) {
			ModelAndView modelAndView = ClientHelper.getDefaultModel(CONFIGURATION_VIEW);
			SimulationConfigurationItem configurationItem = getConfigurationItem(configuration);
			modelAndView.addObject(ViewParameters.SIMULATION_CONFIGURATION, configurationItem);

			return modelAndView;
		} else
			return ProjectController.RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_ADD, method = RequestMethod.GET)
	public ModelAndView addConfiguration(HttpServletRequest request, @PathVariable int projectId) {

		ModelAndView modelAndView = ClientHelper.getDefaultModel(ADDCONFIGURATION_VIEW).addObject(
				ViewParameters.CANCEL_URI, String.format("%s/%s", MappingSettings.PROJECT_ROOT, projectId));
		modelAndView.addObject(ViewParameters.PROJECT_ID, projectId);
		return modelAndView;
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_ADD, method = RequestMethod.POST)
	public ModelAndView addConfiguration(HttpServletRequest request, @PathVariable int projectId,
			@ModelAttribute SimulationConfigurationItemImpl simulationConfigurationItem, BindingResult result) {

		simulationConfigurationValidator.validate(simulationConfigurationItem, result);
		if (result.hasErrors()) {
			ModelAndView modelAndView = ClientHelper.getDefaultModel(ADDCONFIGURATION_VIEW).addObject(
					ViewParameters.CANCEL_URI, String.format("%s/%s", MappingSettings.PROJECT_ROOT, projectId));
			modelAndView.addObject(ViewParameters.PROJECT_ID, projectId);
			modelAndView.addObject(ViewParameters.MODEL_VAR, simulationConfigurationItem);
			FieldError er = result.getFieldError();
			modelAndView.addObject(ViewParameters.ERROR_MSG_VAR, er.getDefaultMessage());
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
			ModelAndView modelAndView = ClientHelper.getDefaultModel(EDITCONFIGURATION_VIEW).addObject(
					ViewParameters.CANCEL_URI,
					String.format("%s/%s", MappingSettings.CONFIGURATION_ROOT, configurationId));
			SimulationConfigurationItem configurationItem = getConfigurationItem(configuration);
			modelAndView.addObject(ViewParameters.MODEL_VAR, configurationItem);

			return modelAndView;
		} else
			return ProjectController.RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_EDIT, method = RequestMethod.POST)
	public ModelAndView editConfiguration(HttpServletRequest request, @PathVariable int configurationId,
			@ModelAttribute SimulationConfigurationItemImpl configurationItem, BindingResult result) {

		simulationConfigurationValidator.validate(configurationItem, result);
		if (result.hasErrors()) {
			ModelAndView modelAndView = ClientHelper.getDefaultModel(EDITCONFIGURATION_VIEW).addObject(
					ViewParameters.CANCEL_URI,
					String.format("%s/%s", MappingSettings.CONFIGURATION_ROOT, configurationId));
			modelAndView.addObject(ViewParameters.MODEL_VAR, configurationItem);
			FieldError er = result.getFieldError();
			modelAndView.addObject(ViewParameters.ERROR_MSG_VAR, er.getDefaultMessage());
			return modelAndView;
		}

		simulationConfigurationService.editConfiguration(configurationId, configurationItem.getName(),
				configurationItem.getDescription());

		return RedirectToConfiguration(configurationId);
	}

	private SimulationConfigurationItem getConfigurationItem(SimulationConfiguration configuration) {
		return simulationConfigurationItemFactory.create(configuration, true);
	}

	public static ModelAndView RedirectToConfiguration(int configurationId) {

		return ClientHelper.getDefaultModel(String.format("redirect:%s/%s", MappingSettings.CONFIGURATION_ROOT,
				configurationId));
	}
}
