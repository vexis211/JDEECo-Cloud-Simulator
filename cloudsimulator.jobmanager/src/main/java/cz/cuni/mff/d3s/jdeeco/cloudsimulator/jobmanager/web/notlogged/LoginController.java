package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.FormRedirectResponse;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.UriParamSettings;

/** Login related requests */
@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(LoginController.class);

	@Resource
	private AppContext appContext;

	@Resource
	private UserService userService;

	public static final String LOGIN_VIEW = "notlogged/login";

	@RequestMapping(value = MappingSettings.LOGIN_ANONYM + "/{anonymousLoginCode}")
	public String anonymousCodeLogin(HttpServletRequest request, @PathVariable String anonymousLoginCode) {
		User user = userService.anonymousLogin(request, anonymousLoginCode);
		if (user == null) {
			// Anonymous login failed.
			// TODO page with message link is forbidden to use.
		}
		return "redirect:" + MappingSettings.MAIN;
	}

	/**
	 * Login
	 */
	@RequestMapping(value = MappingSettings.LOGIN)
	public ModelAndView showForm(HttpServletRequest request, HttpServletResponse response) {
		return defaultModel();
	}

	// TODO reimplement to redirect:
	/**
	 * Check authentication
	 * 
	 * @param login
	 *            Login form data
	 */
	@RequestMapping(value = MappingSettings.CHECKAUTHENTICATION, method = RequestMethod.POST)
	public void validateLogging(final HttpServletRequest request, final HttpServletResponse response,
			@ModelAttribute LoginForm login) {

		User user = userService.findByEmail(login.getEmail());
		FormRedirectResponse redirResponse;

		// user exists and is not activated
		if (user != null && !userService.isActivated(user)) {
			userService.sendActivationEmail(user);
			String actionUri = appContext.getSiteRoot() + "?" + UriParamSettings.FAILURE + "=2";
			redirResponse = FormRedirectResponse.initWithAction(actionUri);
		}
		// redirect to authentication
		else {
			redirResponse = FormRedirectResponse.initWithAction(appContext.getSiteRoot()
					+ MappingSettings.AUTHENTICATION);
			redirResponse.addParameter("email", login.getEmail());
			redirResponse.addParameter("password", login.getPassword());
		}

		try {
			// Redirects to main page with js form to have nice URL and also pass parameters.
			redirResponse.renderWithHeaders(response);
		} catch (IOException ex) {
			logger.error("Error rendering form redirect response.", ex);
		}
	}

	/**
	 * Creates new model with preset default view.
	 * 
	 * @return Instance of new model with default view.
	 */
	private ModelAndView defaultModel() {
		return new ModelAndView(LOGIN_VIEW);
	}
}