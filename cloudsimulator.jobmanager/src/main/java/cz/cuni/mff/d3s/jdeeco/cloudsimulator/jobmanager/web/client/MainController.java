package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.UriParamSettings;

public class MainController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(MainController.class);

	private static final String PROJECTLIST_VIEW = "notlogged/projectList";
	private static final String PROJECT_VIEW = "notlogged/project";
	private static final String CONFIGURATION_VIEW = "notlogged/configuration";
	private static final String EXECUTION_VIEW = "notlogged/execution";

	@RequestMapping(value = MappingSettings.MAIN)
	public ModelAndView showMain(HttpServletRequest request) {

		return new ModelAndView(PROJECTLIST_VIEW);
	}

	@RequestMapping(value = MappingSettings.MAIN, params = UriParamSettings.PROJECTID)
	public ModelAndView showProject(HttpServletRequest request,
			@RequestParam(value = UriParamSettings.PROJECTID) int projectID) {

		return new ModelAndView(PROJECT_VIEW);
	}

	@RequestMapping(value = MappingSettings.MAIN, params = UriParamSettings.CONFIGURATIONID)
	public ModelAndView showConfiguration(HttpServletRequest request,
			@RequestParam(value = UriParamSettings.CONFIGURATIONID) int configurationID) {

		return new ModelAndView(CONFIGURATION_VIEW);
	}

	@RequestMapping(value = MappingSettings.MAIN, params = UriParamSettings.EXECUTIONID)
	public ModelAndView showExecution(HttpServletRequest request,
			@RequestParam(value = UriParamSettings.EXECUTIONID) int executionID) {

		return new ModelAndView(EXECUTION_VIEW);
	}
}
