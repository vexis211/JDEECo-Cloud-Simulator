package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;

public class SimulationStatusUpdateImpl extends WorkerUpdateImpl implements SimulationStatusUpdate {

	private static final long serialVersionUID = -757638661771912962L;

	private int simulationRunId;
	private SimulationStatus simulationStatus;
	private String error;

	public SimulationStatusUpdateImpl(String workerId, int simulationRunId, SimulationStatus simulationStatus) {
		super(workerId);
		
		this.simulationRunId = simulationRunId;
		this.simulationStatus = simulationStatus;
	}

	public SimulationStatusUpdateImpl(String workerId, int simulationRunId, String error) {
		super(workerId);
		
		this.simulationRunId = simulationRunId;
		this.simulationStatus = SimulationStatus.ErrorOccured;
		this.error = error;
	}

	@Override
	public int getSimulationRunId() {
		return simulationRunId;
	}

	@Override
	public SimulationStatus getSimulationStatus() {
		return simulationStatus;
	}

	@Override
	public String getError() {
		return error;
	}
}
