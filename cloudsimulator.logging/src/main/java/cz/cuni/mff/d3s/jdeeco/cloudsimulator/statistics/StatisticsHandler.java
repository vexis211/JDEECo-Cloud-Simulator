package cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics;

public interface StatisticsHandler {

	void write(String id);
	
	void write(String id, boolean value);
	void write(String id, byte value);
	void write(String id, short value);
	void write(String id, int value);
	void write(String id, long value);
	void write(String id, float value);
	void write(String id, double value);
}