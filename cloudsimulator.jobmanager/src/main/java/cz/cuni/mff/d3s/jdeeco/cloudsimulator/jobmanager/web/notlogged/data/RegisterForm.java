package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged.data;

/** Register form */
public class RegisterForm {

	public static final String EMAIL_FIELD = "email";
	public static final String PASSWORD_FIELD = "password";
	public static final String PASSWORD_VERIFY_FIELD = "passwordVerify";
//	public static final String AGREEMENT_FIELD = "agreement";

	private String email;
	private String password;
	private String passwordVerify;
//	private boolean agreement;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordVerify() {
		return passwordVerify;
	}

	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}

//	public boolean getAgreement() {
//		return agreement;
//	}
//
//	public void setAgreement(boolean agreement) {
//		this.agreement = agreement;
//	}
}
