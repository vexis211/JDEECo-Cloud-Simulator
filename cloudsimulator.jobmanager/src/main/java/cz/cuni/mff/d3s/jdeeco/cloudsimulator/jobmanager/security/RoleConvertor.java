package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RoleConvertor  {
	
	private static Map<Integer, String> dbToAppRoles;
	private static Map<String, Integer> appToDbRoles;
	
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";
    
    static {
    	dbToAppRoles = new HashMap<Integer, String>();
    	dbToAppRoles.put(0, ROLE_ANONYMOUS);
    	dbToAppRoles.put(1, ROLE_USER);
    	dbToAppRoles.put(2, ROLE_ADMINISTRATOR);
    	
    	appToDbRoles = new HashMap<String, Integer>();
    	for (Entry<Integer, String> entry : dbToAppRoles.entrySet()) {
    		appToDbRoles.put(entry.getValue(), entry.getKey());
    	}
    }
    
	public static String getRole(int role) {
		return dbToAppRoles.get(role);
	}
	
	public static int getRole(String role) {
		return appToDbRoles.get(role);
	}    
}
