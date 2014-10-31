package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping({"/", ""})
public class IndexController {

	public static final String INDEX_VIEW = "index";

	private final Logger logger = Logger.getLogger(IndexController.class);
	
	@RequestMapping({"/", ""})
	public ModelAndView index(HttpServletRequest request) {
		logger.fatal("IndexController works!!!");
		return defaultModel();
	}

	private ModelAndView defaultModel() {
		return new ModelAndView(INDEX_VIEW);
	}
}
