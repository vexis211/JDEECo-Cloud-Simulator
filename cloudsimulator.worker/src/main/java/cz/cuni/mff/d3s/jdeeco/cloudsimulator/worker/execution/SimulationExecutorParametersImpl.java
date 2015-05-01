package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data.SimulationData;

public class SimulationExecutorParametersImpl implements SimulationExecutorParameters {

	private SimulationData data;
	private SimulationId simulationId;

	public SimulationExecutorParametersImpl(SimulationId simulationId, SimulationData data) {
		this.simulationId = simulationId;
		this.data = data;
	}

	@Override
	public SimulationId getSimulationId() {
		return simulationId;
	}

	@Override
	public SimulationData getSimulationData() {
		return data;
	}

	@Override
	public String getRunExecutionDirectory() {
		return data.getExecutionPath();
	}

	@Override
	public String getRunLogsPath() {
		return data.getLocalLogsPath();
	}
	
	@Override
	public String getRunResultsPath() {
		return data.getLocalResultsPath();
	}

	@Override
	public String getStartupFile() {
		return data.getStartupFile();
	}

	@Override
	public String toString() {
		return String.format("SimulationExecutorParametersImpl [simulationId=%s, data=%s]", simulationId, data);
	}
}
