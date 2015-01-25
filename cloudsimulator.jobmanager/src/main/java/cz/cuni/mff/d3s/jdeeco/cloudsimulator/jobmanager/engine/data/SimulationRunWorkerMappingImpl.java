package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.HashMap;

public class SimulationRunWorkerMappingImpl implements SimulationRunWorkerMapping {

	private HashMap<String, Integer> workerId2SimulationRun = new HashMap<>();
	private HashMap<Integer, String> simulationRun2WorkerId = new HashMap<>();

	private final Object locker = new Object();

	@Override
	public String getWorkerId(int simulationRunId) {
		synchronized (locker) {
			return simulationRun2WorkerId.containsKey(simulationRunId) ? simulationRun2WorkerId.get(simulationRunId)
					: null;
		}
	}

	@Override
	public Integer getSimulationRunId(String workerId) {
		synchronized (locker) {
			return workerId2SimulationRun.containsKey(workerId) ? workerId2SimulationRun.get(workerId) : null;
		}
	}

	@Override
	public void put(String workerId, int simulationRunId) {
		synchronized (locker) {
			workerId2SimulationRun.put(workerId, simulationRunId);
			simulationRun2WorkerId.put(simulationRunId, workerId);
		}
	}
}
