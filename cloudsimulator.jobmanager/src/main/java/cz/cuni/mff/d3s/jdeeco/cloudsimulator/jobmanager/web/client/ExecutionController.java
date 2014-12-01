package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;

@Controller
public class ExecutionController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ExecutionController.class);

	private static final String EXECUTION_VIEW = "main/execution";
	private static final String ADDEXECUTION_VIEW = "main/addExecution";
	private static final String EDITEXECUTION_VIEW = "main/editExecution";

	@RequestMapping(value = MappingSettings.EXECUTION)
	public ModelAndView showExecution(HttpServletRequest request, @PathVariable int executionID) {

		return ClientHelper.getDefaultModel(EXECUTION_VIEW);
	}

	@RequestMapping(value = MappingSettings.EXECUTION_RUN)
	public ModelAndView addExecution(HttpServletRequest request, @PathVariable int configurationID) {

		return ClientHelper.getDefaultModel(ADDEXECUTION_VIEW);
	}

	@RequestMapping(value = MappingSettings.EXECUTION_EDIT)
	public ModelAndView editExecution(HttpServletRequest request, @PathVariable int executionID) {

		return ClientHelper.getDefaultModel(EDITEXECUTION_VIEW);
	}
}
