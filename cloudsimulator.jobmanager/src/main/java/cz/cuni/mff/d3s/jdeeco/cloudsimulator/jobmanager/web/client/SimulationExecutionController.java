package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.SimulationExecutionService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;

@Controller
public class SimulationExecutionController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(SimulationExecutionController.class);

	private static final String EXECUTION_VIEW = "main/execution/simulationExecution";
	//private static final String ADDEXECUTION_VIEW = "main/execution/addSimulationExecution";
	private static final String EDITEXECUTION_VIEW = "main/execution/editSimulationExecution";

	@Resource
	private SimulationExecutionService simulationExecutionService; 
	
	@RequestMapping(value = MappingSettings.EXECUTION)
	public ModelAndView showExecution(HttpServletRequest request, @PathVariable int executionId) {

		return ClientHelper.getDefaultModel(EXECUTION_VIEW);
	}

	@RequestMapping(value = MappingSettings.EXECUTION_RUN)
	public ModelAndView addExecution(HttpServletRequest request, @PathVariable int configurationId) {

		//SimulationExecution execution =
		simulationExecutionService.executeConfiguration(configurationId, ""); // TODO description
		
		//return ClientHelper.getDefaultModel(ADDEXECUTION_VIEW);
		
		return SimulationConfigurationController.RedirectToConfiguration(configurationId);
	}

	@RequestMapping(value = MappingSettings.EXECUTION_EDIT)
	public ModelAndView editExecution(HttpServletRequest request, @PathVariable int executionId) {

		return ClientHelper.getDefaultModel(EDITEXECUTION_VIEW);
	}
}
