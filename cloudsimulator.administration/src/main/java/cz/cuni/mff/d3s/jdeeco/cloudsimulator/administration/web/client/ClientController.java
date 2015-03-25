package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.UserHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;

/**
 * Client related requests
 */
@Controller
public class ClientController {

	@Resource
	private UserService userService;

	@Resource
	private NewPasswordValidator newPasswordValidator;

	@Resource
	private ChangePasswordValidator changePasswordValidator;

	private static final String OK = "Success";

	/**
	 * Validate new-password form. If it was OK, change password and return
	 * success.
	 * 
	 * TODO: Encoding in error message fixed, but do we want to transfer text to the client.
	 * I would prefer another field ("Success", "OldBad", "NoMatch") and processing on the client.
	 * 
	 * @param newPasswordForm
	 *            new-password form
	 * @param result
	 *            result of binding (errors, ...)
	 * @param request
	 *            handled request
	 * @return result for AJAX
	 */
	@RequestMapping(value = MappingSettings.NEWPASSWORD, method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String newPassword(@ModelAttribute NewPasswordForm newPasswordForm, BindingResult result,
			HttpServletRequest request) {
		newPasswordValidator.validate(newPasswordForm, result);

		// errors
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();
			return er.getDefaultMessage();
		}

		userService.changePassword(UserHelper.getAuthenticatedUser().getId(), newPasswordForm.getPassword());
		return OK;
	}

	/**
	 * Validate password-change form. If it was OK, change password and return
	 * success.
	 * 
	 * TODO improvement - Encoding in error message fixed, but do we want to transfer text to the client.
	 * I would prefer another field ("Success", "OldBad", "NoMatch") and processing on the client.
	 * 
	 * @param form
	 *            password-change form
	 * @param result
	 *            result of binding (errors, ...)
	 * 
	 * @return result for AJAX
	 */
	@RequestMapping(value = MappingSettings.CHANGEPASSWORD, method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String changePassword(@ModelAttribute ChangePasswordForm form, BindingResult result) {

		// validation
		changePasswordValidator.validate(form, result);

		// errors
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();
			return er.getDefaultMessage();
		}

		userService.changePassword(UserHelper.getAuthenticatedUser().getId(), form.getNewPassword());
		return OK;
	}
}
