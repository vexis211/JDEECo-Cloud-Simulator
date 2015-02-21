package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.SimulationExecutionService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationExecutionItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories.SimulationExecutionItemFactory;

@Controller
public class SimulationExecutionController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(SimulationExecutionController.class);

	private static final String EXECUTION_VIEW = "main/execution/simulationExecution";
	// private static final String ADDEXECUTION_VIEW =
	// "main/execution/addSimulationExecution";
	private static final String EDITEXECUTION_VIEW = "main/execution/editSimulationExecution";

	@Resource
	private SimulationExecutionService simulationExecutionService;

	@Resource
	private SimulationExecutionItemFactory simulationExecutionItemFactory;
	
	@Resource
	private NavigationPathBuilder navigationPathBuilder;

	@RequestMapping(value = MappingSettings.EXECUTION)
	public ModelAndView showExecution(HttpServletRequest request, @PathVariable int executionId) {

		SimulationExecution execution = simulationExecutionService.getExecutionById(executionId);

		if (execution != null) {
			SimulationExecutionItem executionItem = getExecutionItem(execution);

			ClientModelAndView modelAndView = getDefaultModelAnView(EXECUTION_VIEW).withSimulationExecution(executionItem)
					.withNavigationPath(navigationPathBuilder.buildFromSimulationExecution(executionId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList();
	}

	@RequestMapping(value = MappingSettings.EXECUTION_RUN)
	public ModelAndView addExecution(HttpServletRequest request, @PathVariable int configurationId) {

		// SimulationExecution execution =
		simulationExecutionService.executeConfiguration(configurationId, ""); // TODO
																				// description

		// return ClientHelper.getDefaultModel(ADDEXECUTION_VIEW);

		return SimulationConfigurationController.RedirectToConfiguration(configurationId);
	}

	@RequestMapping(value = MappingSettings.EXECUTION_EDIT)
	public ModelAndView editExecution(HttpServletRequest request, @PathVariable int executionId) {

		return getDefaultModelAnView(EDITEXECUTION_VIEW);
	}


	private SimulationExecutionItem getExecutionItem(SimulationExecution execution) {
		return simulationExecutionItemFactory.create(execution);
	}
	
	private ClientModelAndView getDefaultModelAnView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
