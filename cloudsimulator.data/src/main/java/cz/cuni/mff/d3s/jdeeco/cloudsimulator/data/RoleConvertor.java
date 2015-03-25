package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RoleConvertor  {
	
	private static Map<Integer, String> dbToAppRoles;
	private static Map<String, Integer> appToDbRoles;
	
    static {
    	dbToAppRoles = new HashMap<Integer, String>();
    	dbToAppRoles.put(0, Role.ROLE_ANONYMOUS);
    	dbToAppRoles.put(1, Role.ROLE_USER);
    	dbToAppRoles.put(2, Role.ROLE_ADMINISTRATOR);
    	
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
