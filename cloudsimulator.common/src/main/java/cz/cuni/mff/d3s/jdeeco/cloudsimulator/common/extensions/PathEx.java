package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions;

import java.nio.file.Paths;

public class PathEx {
	public static String combine(String first, String... more) {
		return Paths.get(first, more).toString();
	}
	public static String combine(String first, Object second) {
		return Paths.get(first, second.toString()).toString();
	}
	public static String combine(String first, Object second, Object third) {
		return Paths.get(first, second.toString(), third.toString()).toString();
	}
}
