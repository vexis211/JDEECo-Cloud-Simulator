package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;

/** Forgotten password and password reset requests */
@Controller
public class ForgottenPasswordController {

	private static final String FORGOTTEN_PASSWORD_VIEW = "notlogged/forgottenPassword";
	private static final String FORGOTTEN_PASSWORD_STATE = "forgottenPasswordState";
	private static final String ERROR_MSG_VAR = "errorMsg";
	private static final String ERROR_MODEL_VAR = "model";

	private final int FORM_NOT_SUBMITTED_STATE = 0;
	private final int FORM_ERROR_STATE = 1;
	private final int FORM_SUBMITTED_STATE = 2;

	/**
	 * Where to go after form is canceled.
	 */
	private final String CANCEL_URI = "cancelUri";

	@Resource
	private ForgottenPasswordValidator forgottenPasswordValidator;

	@Resource
	private UserService userService;

	@Resource
	private AppContext appContext;

	/**
	 * Reset password
	 * 
	 * @param resetPasswordCode
	 *            Reset code
	 * @return Redirection to new password
	 */
	@RequestMapping(value = MappingSettings.RESETPASSWORD + "/{resetPasswordCode}")
	public ModelAndView resetPassword(@PathVariable String resetPasswordCode, HttpServletRequest request) {

		User user = userService.resetPasswordLogin(request, resetPasswordCode);

		if (user == null) {
			// User and code does not match.
			return new ModelAndView("redirect:/");
		}

		return new ModelAndView("redirect:" + MappingSettings.NEWPASSWORD);
	}

	@RequestMapping(value = MappingSettings.FORGOTTENPASSWORD, method = RequestMethod.GET)
	public ModelAndView showForm() {
		User user = UserHelper.getAuthenticatedUser();
		if (testUserIsActivated(user)) {
			// User is already activated, he cannot do anything reset password
			return redirectToMainModel();
		}
		return defaultFormView().addObject(FORGOTTEN_PASSWORD_STATE, FORM_NOT_SUBMITTED_STATE);
	}

	/**
	 * Checks if user is activated.
	 *
	 * @param user
	 *            User to check, can be null.
	 * @return True if user is activated otherwise false.
	 */
	private boolean testUserIsActivated(final User user) {
		return user != null && userService.isActivated(user);
	}

	@RequestMapping(value = MappingSettings.FORGOTTENPASSWORD, method = RequestMethod.POST)
	public ModelAndView validateForm(@ModelAttribute ForgottenPasswordForm form, BindingResult result) {
		forgottenPasswordValidator.validate(form, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();
			return renderFormErrors(form, er.getDefaultMessage());
		}

		User user = userService.requestResetPassword(form.getEmail());
		if (user == null) {
			return renderFormErrors(form, "User with specified email does not exists!");
		}

		return renderFormSuccess(form);
	}

	private ModelAndView renderFormErrors(ForgottenPasswordForm form, String errorMessage) {
		return defaultFormView().addObject(FORGOTTEN_PASSWORD_STATE, FORM_ERROR_STATE)
				.addObject(ERROR_MODEL_VAR, form)
				.addObject(ERROR_MSG_VAR, errorMessage);
	}

	private ModelAndView renderFormSuccess(ForgottenPasswordForm form) {
		return defaultFormView().addObject(FORGOTTEN_PASSWORD_STATE, FORM_SUBMITTED_STATE);
	}

	private ModelAndView redirectToMainModel() {
		return new ModelAndView("redirect:" + MappingSettings.MAIN);
	}
	
	private ModelAndView defaultFormView() {
		return new ModelAndView(FORGOTTEN_PASSWORD_VIEW).addObject(CANCEL_URI, appContext.getSiteRoot());
	}
}
