package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;

public class SimulationStatusUpdateImpl extends WorkerUpdateImpl implements SimulationStatusUpdate {

	private static final long serialVersionUID = -757638661771912962L;

	private final SimulationId simulationId;
	private final SimulationStatus simulationStatus;
	private final SimulationExitReason exitReason;
	private final String error;


	public SimulationStatusUpdateImpl(String workerId, SimulationId simulationId, SimulationStatus simulationStatus,
			SimulationExitReason exitReason) {
		super(workerId);

		this.simulationId = simulationId;
		this.simulationStatus = simulationStatus;
		this.exitReason = exitReason;
		this.error = null;
	}

	public SimulationStatusUpdateImpl(String workerId, SimulationId simulationId, String error) {
		super(workerId);

		this.simulationId = simulationId;
		this.simulationStatus = SimulationStatus.ErrorOccured;
		this.exitReason = SimulationExitReason.ExceptionOccured;
		this.error = error;
	}

	@Override
	public SimulationId getSimulationId() {
		return simulationId;
	}

	@Override
	public SimulationStatus getSimulationStatus() {
		return simulationStatus;
	}

	@Override
	public SimulationExitReason getExitReason() {
		return exitReason;
	}

	@Override
	public String getError() {
		return error;
	}
}
