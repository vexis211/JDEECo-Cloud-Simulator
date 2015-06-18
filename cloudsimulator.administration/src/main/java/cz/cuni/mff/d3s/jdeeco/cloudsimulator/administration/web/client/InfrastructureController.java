package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;

@Controller
public class InfrastructureController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(InfrastructureController.class);

	private static final String INFRASTRUCUTRE_VIEW = "main/infrastructure/infrastructure";;
	

	@RequestMapping(value = MappingSettings.INFRASTRUCTURE)
	public ModelAndView showInfrastructure(HttpServletRequest request) {

			ClientModelAndView modelAndView = getDefaultModelAnView(INFRASTRUCUTRE_VIEW);

			return modelAndView;
	}

	private ClientModelAndView getDefaultModelAnView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
