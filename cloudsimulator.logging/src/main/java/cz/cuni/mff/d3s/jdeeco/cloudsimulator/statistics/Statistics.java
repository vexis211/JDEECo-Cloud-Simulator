package cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics;

public class Statistics {
	public static StatisticsHandler Handler = new VoidStatisticsHandler();

	public static void Write(String id) {
		Handler.write(id);
	}
	public static void Write(String id, boolean value) {
		Handler.write(id, value);
	}
	public static void Write(String id, byte value) {
		Handler.write(id, value);
	}
	public static void Write(String id, short value) {
		Handler.write(id, value);
	}
	public static void Write(String id, int value) {
		Handler.write(id, value);
	}
	public static void Write(String id, long value) {
		Handler.write(id, value);
	}
	public static void Write(String id, float value) {
		Handler.write(id, value);
	}
	public static void Write(String id, double value) {
		Handler.write(id, value);
	}
}
