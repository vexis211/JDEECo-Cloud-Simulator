package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;

public final class ClientHelper {

	public static ModelAndView getDefaultModel(String viewName) {
		User user = UserHelper.getAuthenticatedUser();
		
		ModelAndView modelView = new ModelAndView(viewName);
		modelView.addObject("user", user);
		
		return modelView;
	}
}
