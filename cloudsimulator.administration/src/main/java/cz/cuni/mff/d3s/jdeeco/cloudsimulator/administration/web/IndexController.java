package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.notlogged.NotLoggedHelper;

@Controller
public class IndexController {

	public static final String INDEX_VIEW = "index";

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(IndexController.class);

	@Resource
	private UserService userService;
	
	@RequestMapping(value = MappingSettings.INDEX)
	public ModelAndView index(HttpServletRequest request) {
		if (NotLoggedHelper.isUserLoggedIn(userService)) {
			return NotLoggedHelper.redirectToMainModel();
		}
		return defaultModel();
	}

	@RequestMapping(value = MappingSettings.INDEX, params = UriParamSettings.FAILURE)
	public ModelAndView loginFailure(HttpServletRequest request,
			@RequestParam(value = UriParamSettings.FAILURE) String failureReason) {

		return defaultModel().addObject(UriParamSettings.FAILURE, failureReason);
	}

	private ModelAndView defaultModel() {
		return new ModelAndView(INDEX_VIEW);
	}
}
