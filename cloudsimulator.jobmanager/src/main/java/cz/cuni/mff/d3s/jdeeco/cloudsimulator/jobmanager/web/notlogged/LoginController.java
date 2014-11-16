package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;

/** Login related requests */
@Controller
public class LoginController {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(LoginController.class);

	@Resource
	private AppContext appContext;

	@Resource
	private UserService userService;

	@RequestMapping(value = MappingSettings.LOGIN_ANONYM + "/{anonymousLoginCode}")
	public String anonymousCodeLogin(HttpServletRequest request, @PathVariable String anonymousLoginCode) {
		User user = userService.anonymousLogin(request, anonymousLoginCode);
		if (user == null) {
			// Anonymous login failed.
			// TODO page with message link is forbidden to use.
		}
		return "redirect:" + MappingSettings.MAIN;
	}
}