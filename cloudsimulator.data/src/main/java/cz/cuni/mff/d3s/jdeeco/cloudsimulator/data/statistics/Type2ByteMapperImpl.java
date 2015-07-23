package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics;

import java.util.HashMap;
import java.util.Map;

public class Type2ByteMapperImpl implements Type2ByteMapper {

	private static final Map<Class<?>, Byte> mapping = new HashMap<>();
	private static final Map<Byte, Class<?>> reverseMapping = new HashMap<>();

	static {
		mapping.put(boolean.class, (byte) 1);
		mapping.put(byte.class, (byte) 2);
		mapping.put(short.class, (byte) 3);
		mapping.put(int.class, (byte) 4);
		mapping.put(long.class, (byte) 5);
		mapping.put(float.class, (byte) 6);
		mapping.put(double.class, (byte) 7);

		for (Map.Entry<Class<?>, Byte> entry : mapping.entrySet()) {
			reverseMapping.put(entry.getValue(), entry.getKey());
		}
	}

	@Override
	public byte convert(Class<?> type) {
		return mapping.get(type);
	}

	@Override
	public Class<?> convert(byte type) {
		return reverseMapping.get(type);
	}
}
