package cz.cuni.mff.d3s.jdeeco.cloudsimulator.timers;

public class Timer {
	public static TimerHandler Handler = new VoidTimerHandler();
	

	public static void start(String id) {
		Handler.start(id);
	}

	public static void stop(String id) {
		Handler.stop(id);
	}
	
	public static void clear(String id) {
		Handler.reset(id);
	}
}
