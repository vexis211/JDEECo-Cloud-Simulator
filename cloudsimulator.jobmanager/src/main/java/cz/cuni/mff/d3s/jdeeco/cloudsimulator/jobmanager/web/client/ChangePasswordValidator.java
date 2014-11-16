package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.PasswordHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;

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

	private static final String NO_EMAIL = "Email is not specified.";
	private static final String SHORT_PASSWORD = "Zadané heslo musí mať aspoň 8 znakov!";
	private static final String BAD_PASS_CHARS = "Heslo obsahuje neplatné znaky. Iba alfanumerické znaky sú povolené!";
	private static final String PASS_DONOT_MATCH = "Zadané heslo sa nezhoduje!";
	private static final String BAD_PASS = "Vaše súčasné heslo nesúhlasí!";

	@Override
	public void validate(Object target, Errors errors) {
		ChangePasswordForm validatedObj = (ChangePasswordForm) target;
		if (validatedObj == null) {
			errors.rejectValue("email", "error.not-specified", NO_EMAIL);
		} else {
			User loggedUser = UserHelper.getAuthenticatedUser();

			// validation of old password
			if (!passwordEncoder.matches(validatedObj.getOldPassword(), loggedUser.getPassword())) {
				errors.rejectValue(ChangePasswordForm.OLD_PASSWORD_FIELD, "errors.not-match", BAD_PASS);
			}
			// validation for new password
			else if (passwordHelper.isPasswordTooWeak(validatedObj.getNewPassword())) {
				errors.rejectValue(ChangePasswordForm.NEW_PASSWORD_FIELD, "errors.too-weak", SHORT_PASSWORD);

			} else if (passwordHelper.hasPasswordIncorrectCharacters(validatedObj.getNewPassword())) {
				errors.rejectValue(ChangePasswordForm.NEW_PASSWORD_FIELD, "errors.invalid-characters", BAD_PASS_CHARS);
			}
			// validation of password verify field
			else if (!validatedObj.getNewPassword().equals(validatedObj.getNewPasswordVerify())) {
				errors.rejectValue(ChangePasswordForm.NEW_PASSWORD_VERIFY_FIELD, "errors.not-match", PASS_DONOT_MATCH);
			}
		}
	}
}
