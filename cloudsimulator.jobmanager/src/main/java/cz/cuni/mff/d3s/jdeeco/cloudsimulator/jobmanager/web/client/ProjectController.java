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
public class ProjectController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ProjectController.class);

	private static final String PROJECTLIST_VIEW = "main/projectList";
	private static final String PROJECT_VIEW = "main/project";
	private static final String ADDPROJECT_VIEW = "main/addProject";
	private static final String EDITPROJECT_VIEW = "main/editProject";
		
	@RequestMapping(value = MappingSettings.MAIN)
	public ModelAndView showProjectList(HttpServletRequest request) {

		return new ModelAndView(PROJECTLIST_VIEW);
	}

	@RequestMapping(value = MappingSettings.PROJECT, params = UriParamSettings.PROJECTID)
	public ModelAndView showProject(HttpServletRequest request,
			@RequestParam(value = UriParamSettings.PROJECTID) int projectID) {

		return new ModelAndView(PROJECT_VIEW);
	}

	@RequestMapping(value = MappingSettings.PROJECT_ADD)
	public ModelAndView addProject(HttpServletRequest request) {

		return new ModelAndView(ADDPROJECT_VIEW);
	}

	@RequestMapping(value = MappingSettings.PROJECT_EDIT, params = UriParamSettings.PROJECTID)
	public ModelAndView editProject(HttpServletRequest request,
			@RequestParam(value = UriParamSettings.PROJECTID) int projectID) {

		return new ModelAndView(EDITPROJECT_VIEW);
	}
}
