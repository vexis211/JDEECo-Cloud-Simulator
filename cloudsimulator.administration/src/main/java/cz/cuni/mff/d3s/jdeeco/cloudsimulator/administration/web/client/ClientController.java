package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.UserHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;

/**
 * Client related requests
 */
@Controller
public class ClientController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ProjectController.class);

	private static final String NEWPASSWORD_VIEW = "main/password/newPassword";
	private static final String CHANGEPASSWORD_VIEW = "main/password/changePassword";

	private static final String NEWPASSWORDFORM_VIEWPARAMETER = "newPasswordForm";
	private static final String CHANGEPASSWORDFORM_VIEWPARAMETER = "changePasswordForm";

	@Resource
	private UserService userService;

	@Resource
	private NewPasswordValidator newPasswordValidator;

	@Resource
	private ChangePasswordValidator changePasswordValidator;

	@Resource
	private AppContext appContext;

	// TODO - security - should not be allowed!!
	@RequestMapping(value = MappingSettings.NEWPASSWORD, method = RequestMethod.GET)
	public ModelAndView newPassword(HttpServletRequest request) {
		return getDefaultModelAnView(NEWPASSWORD_VIEW);
	}

	@RequestMapping(value = MappingSettings.NEWPASSWORD, method = RequestMethod.POST)
	public ModelAndView newPassword(@ModelAttribute NewPasswordForm newPasswordForm, BindingResult result,
			HttpServletRequest request) {
		newPasswordValidator.validate(newPasswordForm, result);

		// errors
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();

			ModelAndView modelAndView = getDefaultModelAnView(NEWPASSWORD_VIEW).addObject(
					NEWPASSWORDFORM_VIEWPARAMETER, newPasswordForm).withErrorMessage(er.getDefaultMessage());

			return modelAndView;
		}

		userService.changePassword(UserHelper.getAuthenticatedUser().getId(), newPasswordForm.getPassword());

		return ProjectController.RedirectToProjectList(appContext.getSiteRoot());
	}

	@RequestMapping(value = MappingSettings.CHANGEPASSWORD, method = RequestMethod.GET)
	public ModelAndView changePassword(HttpServletRequest request) {
		return getDefaultModelAnView(CHANGEPASSWORD_VIEW)
				.withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.MAIN));
	}

	@RequestMapping(value = MappingSettings.CHANGEPASSWORD, method = RequestMethod.POST)
	public ModelAndView changePassword(@ModelAttribute ChangePasswordForm changePasswordForm, BindingResult result,
			HttpServletRequest request) {
		changePasswordValidator.validate(changePasswordForm, result);

		// errors
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();

			ModelAndView modelAndView = getDefaultModelAnView(CHANGEPASSWORD_VIEW)
					.addObject(CHANGEPASSWORDFORM_VIEWPARAMETER, changePasswordForm)
					.withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.MAIN))
					.withErrorMessage(er.getDefaultMessage());

			return modelAndView;
		}

		userService.changePassword(UserHelper.getAuthenticatedUser().getId(), changePasswordForm.getNewPassword());

		return ProjectController.RedirectToProjectList(appContext.getSiteRoot());
	}

	private ClientModelAndView getDefaultModelAnView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
