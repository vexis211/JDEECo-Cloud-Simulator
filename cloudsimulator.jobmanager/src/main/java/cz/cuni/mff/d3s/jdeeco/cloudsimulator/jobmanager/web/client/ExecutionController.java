package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.UriParamSettings;

@Controller
public class ExecutionController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ExecutionController.class);

	private static final String EXECUTION_VIEW = "main/execution";
	private static final String ADDEXECUTION_VIEW = "main/addExecution";
	private static final String EDITEXECUTION_VIEW = "main/editExecution";


	@RequestMapping(value = MappingSettings.EXECUTION, params = UriParamSettings.EXECUTIONID)
	public ModelAndView showExecution(HttpServletRequest request,
			@RequestParam(value = UriParamSettings.EXECUTIONID) int executionID) {

		return new ModelAndView(EXECUTION_VIEW);
	}

	@RequestMapping(value = MappingSettings.EXECUTION_ADD)
	public ModelAndView addExecution(HttpServletRequest request) {

		return new ModelAndView(ADDEXECUTION_VIEW);
	}

	@RequestMapping(value = MappingSettings.EXECUTION_EDIT, params = UriParamSettings.EXECUTIONID)
	public ModelAndView editExecution(HttpServletRequest request,
			@RequestParam(value = UriParamSettings.EXECUTIONID) int executionID) {

		return new ModelAndView(EDITEXECUTION_VIEW);
	}
}
