package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.SimulationExecutionService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories.SimulationExecutionItemFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;

@Controller
public class SimulationResultsController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(SimulationResultsController.class);

	private static final String SHOWRESULT_VIEW = "main/result/simulationResult";

	@Resource
	private SimulationExecutionService simulationExecutionService;

	@Resource
	private SimulationExecutionItemFactory simulationExecutionItemFactory;

	@Resource
	private NavigationPathBuilder navigationPathBuilder;

	@Resource
	private AppContext appContext;

	@RequestMapping(value = MappingSettings.RESULT_SHOW)
	public ModelAndView showResult(HttpServletRequest request, @PathVariable int executionId) {

		SimulationExecution execution = simulationExecutionService.getExecutionById(executionId);

		if (execution != null) {
			SimulationExecutionItem executionItem = getExecutionItem(execution);

			ClientModelAndView modelAndView = getDefaultModelAndView(SHOWRESULT_VIEW)
					.withSimulationExecution(executionItem)
					.withNavigationPath(navigationPathBuilder.buildFromSimulationResult(executionId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList(appContext.getSiteRoot());
	}

	private SimulationExecutionItem getExecutionItem(SimulationExecution execution) {
		return simulationExecutionItemFactory.create(execution);
	}

	private ClientModelAndView getDefaultModelAndView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
