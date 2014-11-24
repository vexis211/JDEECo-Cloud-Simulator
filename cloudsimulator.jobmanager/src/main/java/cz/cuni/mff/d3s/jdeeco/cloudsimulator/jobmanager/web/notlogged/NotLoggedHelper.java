package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;

public final class NotLoggedHelper {

	public static boolean isUserLoggedIn(UserService userService) {
		User user = UserHelper.getAuthenticatedUser();
		return user != null && userService.isActivated(user);
	}
	
	public static ModelAndView redirectToMainModel() {
		return new ModelAndView("redirect:" + MappingSettings.MAIN);
	}
}
