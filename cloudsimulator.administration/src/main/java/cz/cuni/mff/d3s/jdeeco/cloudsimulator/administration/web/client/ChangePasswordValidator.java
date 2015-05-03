package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.PasswordHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.UserHelper;

/** Validator for password changing */
public class ChangePasswordValidator implements Validator {

	@Resource
	private PasswordHelper passwordHelper;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordForm.class.equals(clazz);
	}

	private static final String OLD_PASSWORD_NOT_CORRECT_MESSAGE = "Your old password is not correct!";

	@Override
	public void validate(Object target, Errors errors) {
		ChangePasswordForm validatedObj = (ChangePasswordForm) target;
		if (validatedObj == null) {
			errors.rejectValue("email", "error.not-specified", NewPasswordValidator.EMAIL_NOT_SPECIFIED_MESSAGE);
		} else {
			User loggedUser = UserHelper.getAuthenticatedUser();

			// validation of old password
			if (!passwordEncoder.matches(validatedObj.getOldPassword(), loggedUser.getPassword())) {
				errors.rejectValue(ChangePasswordForm.OLD_PASSWORD_FIELD, "errors.not-match", OLD_PASSWORD_NOT_CORRECT_MESSAGE);
			}
			// validation for new password
			else if (passwordHelper.isPasswordTooWeak(validatedObj.getNewPassword())) {
				errors.rejectValue(ChangePasswordForm.NEW_PASSWORD_FIELD, "errors.too-weak", NewPasswordValidator.PASSWORD_LENGTH_MESSAGE);

			} else if (passwordHelper.hasPasswordIncorrectCharacters(validatedObj.getNewPassword())) {
				errors.rejectValue(ChangePasswordForm.NEW_PASSWORD_FIELD, "errors.invalid-characters", NewPasswordValidator.PASSWORD_INVALID_CHARACTERS_MESSAGE);
			}
			// validation of password verify field
			else if (!validatedObj.getNewPassword().equals(validatedObj.getNewPasswordVerify())) {
				errors.rejectValue(ChangePasswordForm.NEW_PASSWORD_VERIFY_FIELD, "errors.not-match", NewPasswordValidator.PASSWORD_NOT_MATCH_MESSAGE);
			}
		}
	}
}
