package cz.cuni.mff.d3s.jdeeco.cloudsimulator.timers;

public interface TimerHandler {

	void start(String id);
	void stop(String id);
	void reset(String id);
}