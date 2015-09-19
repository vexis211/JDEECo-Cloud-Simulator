package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import java.util.List;
import java.util.stream.Collectors;

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
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.SimulationResultService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionStatisticItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories.SimulationExecutionItemFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories.SimulationExecutionStatisticItemFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecutionStatistic;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics.Type2ByteMapper;

@Controller
public class SimulationResultsController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(SimulationResultsController.class);

	private static final String SHOWRESULT_VIEW = "main/result/simulationResult";

	@Resource
	private SimulationExecutionService simulationExecutionService;

	@Resource
	private SimulationResultService simulationResultService;

	@Resource
	private SimulationExecutionItemFactory simulationExecutionItemFactory;

	@Resource
	private SimulationExecutionStatisticItemFactory simulationExecutionStatisticItemFactory;

	@Resource
	private NavigationPathBuilder navigationPathBuilder;

	@Resource
	private Type2ByteMapper type2ByteMapper;

	@Resource
	private AppContext appContext;

	@RequestMapping(value = MappingSettings.RESULT_SHOW)
	public ModelAndView showResult(HttpServletRequest request, @PathVariable int executionId) {

		SimulationExecution execution = simulationExecutionService.getExecutionById(executionId);

		if (execution != null) {
			SimulationExecutionItem executionItem = getExecutionItem(execution);

			List<SimulationExecutionStatistic> executionStatistics = simulationResultService
					.listExecutionStatistics(executionId);
			List<SimulationExecutionStatisticItem> executionStatisticItems = executionStatistics.stream()
					.map(x -> getExecutionStatisticItem(x)).collect(Collectors.toList());

			ClientModelAndView modelAndView = getDefaultModelAndView(SHOWRESULT_VIEW)
					.withSimulationExecution(executionItem).withSimulationExecutionStatistics(executionStatisticItems)
					.withNavigationPath(navigationPathBuilder.buildFromSimulationResult(executionId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList(appContext.getSiteRoot());
	}

	private SimulationExecutionItem getExecutionItem(SimulationExecution execution) {
		return simulationExecutionItemFactory.create(execution);
	}

	private SimulationExecutionStatisticItem getExecutionStatisticItem(
			SimulationExecutionStatistic executionStatistic) {
		byte dataTypeInByte = executionStatistic.getDataType();
		Class<?> clazz = type2ByteMapper.convert(dataTypeInByte);
		return simulationExecutionStatisticItemFactory.create(executionStatistic, clazz, false, false);
	}

	private ClientModelAndView getDefaultModelAndView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
