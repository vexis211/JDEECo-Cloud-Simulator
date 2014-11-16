package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {

	public static final String INDEX_VIEW = "index";
	public static final String INDEX_FAILURE_PARAM = "failure";

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(IndexController.class);
	
	@RequestMapping(value = MappingSettings.INDEX)
	public ModelAndView index(HttpServletRequest request) {
		return defaultModel();
	}

	private ModelAndView defaultModel() {
		return new ModelAndView(INDEX_VIEW);
	}
}
