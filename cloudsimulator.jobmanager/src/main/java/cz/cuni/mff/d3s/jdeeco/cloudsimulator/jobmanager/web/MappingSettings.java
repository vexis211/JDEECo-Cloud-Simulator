package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web;

public class MappingSettings {

	public static final String INDEX = "";

	public static final String LOGIN_ANONYM = "/anonym/login";

	public static final String ERROR_400 = "/errors/400";
	public static final String ERROR_403 = "/errors/403";
	public static final String ERROR_404 = "/errors/404";
	public static final String ERROR_405 = "/errors/405";
	public static final String ERROR_500 = "/errors/500";
	public static final String ERROR_EXCEPTIONTHROWABLE = "/errors/exceptionThrowable";

	public static final String FORGOTTENPASSWORD = "/client/forgottenpassword";
	public static final String ACTIVATION = "/client/activate";
	public static final String AUTHENTICATION = "/client/auth";
	public static final String CHANGEPASSWORD = "/client/changepassword";
	public static final String LOGOUT = "/client/logout";
	public static final String NEWPASSWORD = "/client/newpassword";
	public static final String REGISTRATION = "/client/register";
	public static final String RESETPASSWORD = "/client/resetpassword";

	public static final String MAIN = "/main";

	
	public static String GetFullUri(String root, String subPage) {
		if (root.endsWith("/") && subPage.startsWith("/")){
			return root + subPage.substring(1); // we dont want double slash
		}
		return root + subPage;
	}
	
}
