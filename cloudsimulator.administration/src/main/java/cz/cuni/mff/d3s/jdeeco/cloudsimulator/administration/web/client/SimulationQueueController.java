package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.SimulationExecutionService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories.SimulationExecutionItemFactory;

@Controller
public class SimulationQueueController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(SimulationQueueController.class);

	private static final String SIMULATIONQUEUE_VIEW = "main/queue/simulationQueue";

	@Resource
	private SimulationExecutionService simulationExecutionService;

	@Resource
	private SimulationExecutionItemFactory simulationExecutionItemFactory;

	@RequestMapping(value = MappingSettings.SIMULATIONQUEUE)
	public ModelAndView showSimulationQueue(HttpServletRequest request) {

		List<SimulationExecution> executions = simulationExecutionService.listAllNotCompletedExecutions();		
		List<SimulationExecutionItem> executionItems = getExecutionItems(executions);

		ClientModelAndView modelAndView = getDefaultModelAndView(SIMULATIONQUEUE_VIEW).withSimulationExecutions(
				executionItems);

		return modelAndView;
	}

	private List<SimulationExecutionItem> getExecutionItems(List<SimulationExecution> executions) {

		List<SimulationExecutionItem> executionItems = executions.stream()
				.sorted((ex1, ex2) -> ex1.getId().compareTo(ex2.getId()))
				.map(ex -> simulationExecutionItemFactory.create(ex)).collect(Collectors.toList());

		return executionItems;
	}

	private ClientModelAndView getDefaultModelAndView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
