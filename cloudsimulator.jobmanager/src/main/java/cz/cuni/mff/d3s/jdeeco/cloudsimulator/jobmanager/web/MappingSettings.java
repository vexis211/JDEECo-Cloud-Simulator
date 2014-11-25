package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web;

public class MappingSettings {

	/* *************************** not logged in *************************** */
	public static final String INDEX = "";

	public static final String LOGIN_ANONYM = "/anonym/login";

	public static final String FORGOTTENPASSWORD = "/client/forgottenpassword";
	public static final String ACTIVATION = "/client/activate";
	public static final String AUTHENTICATION = "/client/auth";
	public static final String REGISTRATION = "/client/register";
	public static final String RESETPASSWORD = "/client/resetpassword";

	
	/* *************************** logged in *************************** */
	public static final String ACTION = "/action";
	public static final String MAIN = "/main";

	
	public static final String NEWPASSWORD = ACTION + "/client/newpassword";
	public static final String CHANGEPASSWORD = ACTION + "/client/changepassword";
	public static final String LOGOUT = ACTION + "/client/logout";

	
	public static final String PROJECT = MAIN + "/project";
	public static final String PROJECT_ADD = MAIN + "/project/add";
	public static final String PROJECT_EDIT = MAIN + "/project/edit";

	public static final String CONFIGURATION = MAIN + "/configuration";
	public static final String CONFIGURATION_ADD = MAIN + "/configuration/add";
	public static final String CONFIGURATION_EDIT = MAIN + "/configuration/edit";

	public static final String EXECUTION = MAIN + "/execution";
	public static final String EXECUTION_ADD = MAIN + "/execution/add";
	public static final String EXECUTION_EDIT = MAIN + "/execution/edit";


	public static final String INFRASTRUCTURE = MAIN + "/infrastructure";
	public static final String SIMULATIONQUEUE = MAIN + "/simulationqueue";
	
	public static String GetFullUri(String root, String subPage) {
		if (root.endsWith("/") && subPage.startsWith("/")){
			return root + subPage.substring(1); // we don't want double slash
		}
		return root + subPage;
	}
	
}
