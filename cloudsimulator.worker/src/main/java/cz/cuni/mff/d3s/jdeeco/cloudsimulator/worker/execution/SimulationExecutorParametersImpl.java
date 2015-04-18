package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data.SimulationData;

public class SimulationExecutorParametersImpl implements SimulationExecutorParameters {

	private SimulationData data;
	private int simulationRunId;

	public SimulationExecutorParametersImpl(int simulationRunId, SimulationData data) {
		this.simulationRunId = simulationRunId;
		this.data = data;
	}

	@Override
	public int getSimulationRunId() {
		return simulationRunId;
	}

	@Override
	public String getExecutionPath() {
		return data.getExecutionPath();
	}

	@Override
	public String getLogPath() {
		return data.getLogsPath();
	}

	@Override
	public SimulationData getSimulationData() {
		return data;
	}
}
