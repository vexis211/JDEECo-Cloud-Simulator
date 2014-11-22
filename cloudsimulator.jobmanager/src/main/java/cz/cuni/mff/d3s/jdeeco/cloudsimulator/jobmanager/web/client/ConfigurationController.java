package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.UriParamSettings;

public class ConfigurationController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ConfigurationController.class);

	private static final String CONFIGURATION_VIEW = "main/configuration";
	private static final String ADDCONFIGURATION_VIEW = "main/addConfiguration";
	private static final String EDITCONFIGURATION_VIEW = "main/editConfiguration";


	@RequestMapping(value = MappingSettings.CONFIGURATION, params = UriParamSettings.CONFIGURATIONID)
	public ModelAndView showConfiguration(HttpServletRequest request,
			@RequestParam(value = UriParamSettings.CONFIGURATIONID) int configurationID) {

		return new ModelAndView(CONFIGURATION_VIEW);
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_ADD)
	public ModelAndView addConfiguration(HttpServletRequest request) {

		return new ModelAndView(ADDCONFIGURATION_VIEW);
	}

	@RequestMapping(value = MappingSettings.CONFIGURATION_EDIT, params = UriParamSettings.CONFIGURATIONID)
	public ModelAndView editConfiguration(HttpServletRequest request,
			@RequestParam(value = UriParamSettings.CONFIGURATIONID) int configurationID) {

		return new ModelAndView(EDITCONFIGURATION_VIEW);
	}
}
