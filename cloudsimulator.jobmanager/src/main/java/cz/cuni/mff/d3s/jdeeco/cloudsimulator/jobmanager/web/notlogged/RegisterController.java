package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.UserOperationErrorType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.UserOperationException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils.HTMLHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;

/**
 * Register form for users.
 */
@Controller
public class RegisterController {

	/**
	 * Logging.
	 */
	private Logger logger = Logger.getLogger(RegisterController.class);

	/**
	 * Name of register page template.
	 */
	private static final String REGISTER_VIEW = "notlogged/register";

	/**
	 * State of registration. Can have values: 0 - no submitted form 1 - submitted form contains errors 2 - activation
	 * email link sent
	 */
	private static final String REGISTER_STATE_VAR = "registerState";

	/**
	 * Registration error message.
	 */
	private static final String ERROR_MSG_VAR = "registerErrorMsg";

	/**
	 * Variable with values for register form.
	 */
	private static final String ERROR_MODEL_VAR = "registerModel";

	/**
	 * Where to go after form is canceled.
	 */
	private static final String CANCEL_URI = "cancelUri";

	/**
	 * Logged user.
	 */
	@SuppressWarnings("unused")
	private static final String LOGGED_USER_VAR = "user";

	private static final String EMAIL_IN_USE = "Email address is already in use, for password reset use this %s.";
	private static final String FILL_ALL_ENTRIES = "You must fill all entries!";

	/**
	 * Validation of register form results.
	 */
	@Resource
	private RegisterValidator registerValidator;

	/**
	 * User operations.
	 */
	@Resource
	private UserService userService;

	/**
	 * The application context.
	 */
	@Resource
	private AppContext appContext;

	@RequestMapping(value = MappingSettings.REGISTRATION, method = RequestMethod.GET)
	public ModelAndView showForm() throws UnsupportedEncodingException {
		User user = UserHelper.getAuthenticatedUser();
		if (testUserIsActivated(user)) {
			// User is already activated, he cannot do anything with
			// registration.
			return redirectToMainModel();
		}

		return defaultModel();
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

	@RequestMapping(value = MappingSettings.REGISTRATION, method = RequestMethod.POST)
	public ModelAndView validateForm(@ModelAttribute RegisterForm registerForm, BindingResult result) {
		User loggedUser = UserHelper.getAuthenticatedUser();
		if (testUserIsActivated(loggedUser)) {
			// User is already activated, he cannot be activated again.
			logger.debug(String.format("Already activated user with id '%d' is trying to register.", loggedUser.getId()));
			return redirectToMainModel();
		}
		registerValidator.validate(registerForm, result);
		if (registerValidator.isEmpty(registerForm)) {
			return renderEmptyError(registerForm, result);
		} else if (result.hasErrors()) {
			return renderErrors(registerForm, result);
		}
		try {
			User user = userService.registerUser(registerForm.getEmail(), registerForm.getPassword());

			logger.info(String.format("User %s has been succesfully registered.", user.getEmail()));
		} catch (UserOperationException ex) {
			return renderRegisterError(registerForm, result, ex.getErrorType());
		} catch (Exception ex) {
			// Some error happens, probably email is already used.
			logger.error("Error registering user", ex);
		}
		return renderSuccess();
	}

	/**
	 * @return ModelAndView of success register page.
	 */
	private ModelAndView renderSuccess() {
		return defaultModel().addObject(REGISTER_STATE_VAR, 2);
	}

	/**
	 * Renders errors when there are empty values.
	 *
	 * @param form
	 *            Posted form.
	 * @param result
	 *            Validation results.
	 * @return Model and view.
	 */
	private ModelAndView renderEmptyError(final RegisterForm form, final BindingResult result) {
		ModelAndView model = defaultModel().addObject(REGISTER_STATE_VAR, 1);
		model.addObject(ERROR_MSG_VAR, FILL_ALL_ENTRIES);
		model.addObject(ERROR_MODEL_VAR, form);
		return model;
	}

	/**
	 * Renders registering exception error.
	 *
	 * @param form
	 *            Posted form.
	 * @param result
	 *            Results of validation.
	 * @param errorType
	 *            Type of registering error.
	 * @return ModelAndView with errors.
	 */
	private ModelAndView renderRegisterError(final RegisterForm form, BindingResult result,
			final UserOperationErrorType errorType) {
		if (errorType.equals(UserOperationErrorType.EMAIL_ALREADY_REGISTERED)) {
			String forgottenPasswordLink = HTMLHelper.createLink(MappingSettings.FORGOTTENPASSWORD, "site");
			result.rejectValue(RegisterForm.EMAIL_FIELD, "error.not-already-used",
					String.format(EMAIL_IN_USE, forgottenPasswordLink));
			return renderErrors(form, result);
		}
		return renderSuccess();
	}

	/**
	 * Renders errors in values.
	 *
	 * @param form
	 *            Posted form.
	 * @param result
	 *            Validation results.
	 * @return Model and view.
	 */
	private ModelAndView renderErrors(final RegisterForm form, final BindingResult result) {
		ModelAndView model = defaultModel().addObject(REGISTER_STATE_VAR, 1);
		model.addObject(ERROR_MODEL_VAR, form);
		return model;
	}

	/**
	 * @return Redirect to main view.
	 */
	private ModelAndView redirectToMainModel() {
		return new ModelAndView("redirect:" + MappingSettings.MAIN);
	}
	
	/**
	 * @return New instance of default register ModelAndView.
	 */
	private ModelAndView defaultModel() {
		return new ModelAndView(REGISTER_VIEW).addObject(CANCEL_URI, appContext.getSiteRoot());
	}
}
