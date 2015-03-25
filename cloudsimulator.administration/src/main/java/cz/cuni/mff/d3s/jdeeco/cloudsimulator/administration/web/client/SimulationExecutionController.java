package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import java.util.Date;

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

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.SimulationExecutionService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories.SimulationExecutionItemFactory;

@Controller
public class SimulationExecutionController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(SimulationExecutionController.class);

	private static final String EXECUTION_VIEW = "main/execution/simulationExecution";
	private static final String ADDEXECUTION_VIEW = "main/execution/addSimulationExecution";
	private static final String EDITEXECUTION_VIEW = "main/execution/editSimulationExecution";

	@Resource
	private SimulationExecutionService simulationExecutionService;

	@Resource
	private SimulationExecutionItemFactory simulationExecutionItemFactory;

	@Resource
	private SimulationExecutionValidator simulationExecutionValidator;

	@Resource
	private SimulationExecutionEditValidator simulationExecutionEditValidator; 
	
	@Resource
	private NavigationPathBuilder navigationPathBuilder;

	@Resource
	private AppContext appContext;

	@RequestMapping(value = MappingSettings.EXECUTION)
	public ModelAndView showExecution(HttpServletRequest request, @PathVariable int executionId) {

		SimulationExecution execution = simulationExecutionService.getExecutionById(executionId);

		if (execution != null) {
			SimulationExecutionItem executionItem = getExecutionItem(execution);

			ClientModelAndView modelAndView = getDefaultModelAndView(EXECUTION_VIEW).withSimulationExecution(
					executionItem).withNavigationPath(navigationPathBuilder.buildFromSimulationExecution(executionId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList(appContext.getSiteRoot());
	}

	@RequestMapping(value = MappingSettings.EXECUTION_RUN, method = RequestMethod.GET)
	public ModelAndView addExecution(HttpServletRequest request, @PathVariable int configurationId) {

		ClientModelAndView modelAndView = getDefaultModelAndView(ADDEXECUTION_VIEW)
				.withSimulationConfigurationId(configurationId)
				.withCancelUri(
						MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.CONFIGURATION_ROOT,
								configurationId))
				.withNavigationPath(navigationPathBuilder.buildFromSimulationConfiguration(configurationId));
		
		modelAndView.addObject("defaultEndDateString", SimulationExecutionItem.TODATE_FORMAT.format(new Date()));
		
		return modelAndView;
	}

	@RequestMapping(value = MappingSettings.EXECUTION_RUN, method = RequestMethod.POST)
	public ModelAndView addExecution(HttpServletRequest request, @PathVariable int configurationId,
			@ModelAttribute SimulationExecutionItemImpl simulationExecutionItem, BindingResult result) {

		simulationExecutionValidator.validate(simulationExecutionItem, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();

			ModelAndView modelAndView = getDefaultModelAndView(ADDEXECUTION_VIEW)
					.withCancelUri(
							MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.CONFIGURATION_ROOT,
									configurationId)).withSimulationExecution(simulationExecutionItem)
					.withErrorMessage(er.getDefaultMessage())
					.withNavigationPath(navigationPathBuilder.buildFromSimulationConfiguration(configurationId));

			return modelAndView;
		}

		simulationExecutionService.executeConfiguration(configurationId, simulationExecutionItem.getDescription(),
				simulationExecutionItem.getRunCount(), simulationExecutionItem.getEndSpecificationType(),
				simulationExecutionItem.getEndDate());

		return SimulationConfigurationController.RedirectToConfiguration(appContext.getSiteRoot(), configurationId);
	}

	@RequestMapping(value = MappingSettings.EXECUTION_EDIT, method = RequestMethod.GET)
	public ModelAndView editExecution(HttpServletRequest request, @PathVariable int executionId) {

		SimulationExecution execution = simulationExecutionService.getExecutionById(executionId);

		if (execution != null) {

			SimulationExecutionItem executionItem = getExecutionItem(execution);

			ModelAndView modelAndView = getDefaultModelAndView(EDITEXECUTION_VIEW)
					.withSimulationConfigurationId(execution.getSimulationConfiguration().getId())
					.withCancelUri(
							MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.EXECUTION_ROOT,
									executionId)).withSimulationExecution(executionItem)
					.withNavigationPath(navigationPathBuilder.buildFromSimulationExecution(executionId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList(appContext.getSiteRoot());
	}

	@RequestMapping(value = MappingSettings.EXECUTION_EDIT, method = RequestMethod.POST)
	public ModelAndView editExecution(HttpServletRequest request, @PathVariable int executionId,
			@ModelAttribute SimulationExecutionItemImpl simulationExecutionItem, BindingResult result) {

		simulationExecutionEditValidator.validate(simulationExecutionItem, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();
			SimulationExecution execution = simulationExecutionService.getExecutionById(executionId);

			ModelAndView modelAndView = getDefaultModelAndView(EDITEXECUTION_VIEW)
					.withSimulationConfigurationId(execution.getSimulationConfiguration().getId())
					.withCancelUri(
							MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.EXECUTION_ROOT,
									executionId)).withSimulationExecution(simulationExecutionItem)
					.withErrorMessage(er.getDefaultMessage())
					.withNavigationPath(navigationPathBuilder.buildFromSimulationExecution(executionId));

			return modelAndView;
		}

		simulationExecutionService.setDescription(executionId, simulationExecutionItem.getDescription());

		return RedirectToExecution(executionId);
	}


	@RequestMapping(value = MappingSettings.EXECUTION_STOP, method = RequestMethod.GET)
	public ModelAndView stopExecution(HttpServletRequest request, @PathVariable int executionId) {

		simulationExecutionService.stopExecution(executionId);
		
		return RedirectToExecution(executionId);
	}
	
	private SimulationExecutionItem getExecutionItem(SimulationExecution execution) {
		return simulationExecutionItemFactory.create(execution);
	}

	public ModelAndView RedirectToExecution(int executionId) {
		return RedirectToExecution(appContext.getSiteRoot(), executionId);
	}

	public static ModelAndView RedirectToExecution(String siteRoot, int executionId) {
		return new ModelAndView("redirect:"
				+ MappingSettings.GetFullUri(siteRoot, MappingSettings.EXECUTION_ROOT, executionId));
	}

	private ClientModelAndView getDefaultModelAndView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
