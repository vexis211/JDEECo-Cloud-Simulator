package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure;

import java.util.HashMap;
import java.util.Map;

public enum WorkerPowerState {

	NOSTATE(0), RUNNING(1), PAUSED(3), SHUTDOWN(4), // the VM is powered off
	CRASHED(6), SUSPENDED(7);

	private static Map<Integer, WorkerPowerState> reverseMapping = new HashMap<>();

	static {
		for (WorkerPowerState powerState : WorkerPowerState.values()) {
			reverseMapping.put(powerState.state, powerState);
		}
	}

	private int state;

	private WorkerPowerState(int state) {
		this.state = state;
	}

	
	public static WorkerPowerState fromInt(int powerState) {
		return reverseMapping.get(powerState);
	}
}
