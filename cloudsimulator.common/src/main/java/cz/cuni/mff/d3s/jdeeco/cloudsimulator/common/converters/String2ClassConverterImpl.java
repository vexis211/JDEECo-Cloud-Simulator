package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.converters;

import java.util.HashMap;
import java.util.Map;

public class String2ClassConverterImpl implements String2ClassConverter {

	private static final Map<String, Class<?>> mapping = new HashMap<>();
	private static final Map<Class<?>, String> reverseMapping = new HashMap<>();

	static {
		addMappingItem(boolean.class);
		addMappingItem(byte.class);
		addMappingItem(short.class);
		addMappingItem(int.class);
		addMappingItem(long.class);
		addMappingItem(float.class);
		addMappingItem(double.class);
		addMappingItem(String.class);
	}
	

	private static void addMappingItem(Class<?> clazz) {
		String simpleName = clazz.getSimpleName();
		mapping.put(simpleName.toLowerCase(), clazz);
		reverseMapping.put(clazz, simpleName); // this simple name goes back to app, therefore it is not lowercased
	}

	@Override
	public Class<?> convert(String typeString) {
		return mapping.get(typeString.toLowerCase());
	}

	@Override
	public String convert(Class<?> type) {
		return reverseMapping.get(type);
	}
}
