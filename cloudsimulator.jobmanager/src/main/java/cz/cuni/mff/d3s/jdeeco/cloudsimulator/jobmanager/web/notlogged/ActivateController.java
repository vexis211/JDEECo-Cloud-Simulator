package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.UriParamSettings;

/** User activation requests */
@Controller
public class ActivateController {

	@Resource
	private AppContext appContext;

	@Resource
	private UserService userService;
		
	@RequestMapping(value = MappingSettings.ACTIVATION + "/{activationCode}")
	public String activateUser(@PathVariable String activationCode, HttpServletRequest request) {
    	User loggedUser = UserHelper.getAuthenticatedUser();
    	if (testIsUserActivated(loggedUser)) {
    	    // Cannot activate already activated user.
    	    return renderOK();
    	}
	    
		User user = userService.activateUser(request, activationCode);
		if (user == null) {
			// Cannot be activated.
			return renderInvalidLink();
		}
		
		return renderOK();
	}
	
	/**
	 * Checks if user is already activated.
	 * 
	 * @param user User or null.
	 * @return True if user is activated otherwise false.
	 */
	private boolean testIsUserActivated(final User user) {
	    return user != null && userService.isActivated(user);
	}

	private String renderInvalidLink() {
		return String.format("redirect:%s?%s=3", appContext.getSiteRoot(), UriParamSettings.FAILURE); // TODO link
	}

	private String renderOK() {
		return "redirect:" + MappingSettings.MAIN;
	}
}
