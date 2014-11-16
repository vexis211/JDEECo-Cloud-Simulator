package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;

/** Forgotten password and password reset requests */
@Controller
public class ForgottenPasswordController {

	private final String FORGOTTEN_PASSWORD_VIEW = "notlogged/forgottenPassword";
	private final String FORGOTTEN_PASSWORD_STATE = "forgottenPasswordState";
	private final String ERROR_MSG_VAR = "errorMsg";
	
	private final int FORM_NOT_SUBMITTED_STATE = 0;
	private final int FORM_ERROR_STATE = 1;
	private final int FORM_SUBMITTED_STATE = 2;
	private final int FORM_NONEXITENT_STATE = 3;

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
	
	/** Reset password
	 * 
	 * @param resetPasswordCode Reset code
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
		return defaultFormView()
			.addObject(FORGOTTEN_PASSWORD_STATE, FORM_NOT_SUBMITTED_STATE);
	}
	
	@RequestMapping(value = MappingSettings.FORGOTTENPASSWORD, method = RequestMethod.POST)
	public ModelAndView validateForm(@ModelAttribute ForgottenPasswordForm form, BindingResult errors) {
		forgottenPasswordValidator.validate(form, errors);
		if (errors.hasErrors()) {
			return renderFormErrors(form, errors);
		}
		return renderFormSuccess(form);
	}
	
	private ModelAndView renderFormErrors(ForgottenPasswordForm form, BindingResult errors) {
		return defaultFormView().addObject(FORGOTTEN_PASSWORD_STATE, FORM_ERROR_STATE)
			.addObject(ERROR_MSG_VAR, "Incorrect email address!"); // TODO reimplement
	}
	
	private ModelAndView renderFormSuccess(ForgottenPasswordForm form) {
		User user = userService.requestResetPassword(form.getEmail());
		if(user == null)
			return defaultFormView().addObject(FORGOTTEN_PASSWORD_STATE, FORM_NONEXITENT_STATE);
		else
			return defaultFormView().addObject(FORGOTTEN_PASSWORD_STATE, FORM_SUBMITTED_STATE);
	}
	
	private ModelAndView defaultFormView() {
		return new ModelAndView(FORGOTTEN_PASSWORD_VIEW)
			.addObject(CANCEL_URI, appContext.getSiteRoot());
	}	
}
