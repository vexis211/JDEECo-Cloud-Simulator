package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

/** Form for forgotten password */
public class ForgottenPasswordForm {

	private String email;
	
	public static final String EMAIL_FIELD = "email";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
