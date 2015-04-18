package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;

public class SimulationStatusUpdateImpl extends WorkerUpdateImpl implements SimulationStatusUpdate {

	private static final long serialVersionUID = -757638661771912962L;

	private final int simulationExecutionId;
	private final int simulationRunId;
	private final SimulationStatus simulationStatus;
	private final String error;

	public SimulationStatusUpdateImpl(String workerId, int simulationExecutionId, int simulationRunId, SimulationStatus simulationStatus) {
		super(workerId);
		
		this.simulationExecutionId = simulationExecutionId;
		this.simulationRunId = simulationRunId;
		this.simulationStatus = simulationStatus;
		this.error = null;
	}

	public SimulationStatusUpdateImpl(String workerId, int simulationExecutionId, int simulationRunId, String error) {
		super(workerId);
		
		this.simulationExecutionId = simulationExecutionId;
		this.simulationRunId = simulationRunId;
		this.simulationStatus = SimulationStatus.ErrorOccured;
		this.error = error;
	}

	@Override
	public int getSimulationExecutionId() {
		return simulationExecutionId;
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
