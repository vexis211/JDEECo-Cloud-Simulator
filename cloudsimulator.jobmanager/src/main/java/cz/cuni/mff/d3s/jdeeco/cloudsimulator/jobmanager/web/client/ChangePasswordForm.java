package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

/**
 * Form for changing password.
 */
public class ChangePasswordForm {

	// field names
	public static final String OLD_PASSWORD_FIELD = "oldPassword";
	public static final String NEW_PASSWORD_FIELD = "newPassword";
	public static final String NEW_PASSWORD_VERIFY_FIELD = "newPasswordVerify";

	// fields
	private String oldPassword;
	private String newPassword;
	private String newPasswordVerify;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordVerify() {
		return newPasswordVerify;
	}

	public void setNewPasswordVerify(String newPasswordVerify) {
		this.newPasswordVerify = newPasswordVerify;
	}
}
