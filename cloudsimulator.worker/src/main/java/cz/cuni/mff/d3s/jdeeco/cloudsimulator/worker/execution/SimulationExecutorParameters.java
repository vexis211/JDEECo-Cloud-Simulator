package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data.SimulationData;

public interface SimulationExecutorParameters {

	int getSimulationRunId();

	SimulationData getSimulationData();
	
	String getExecutionPath();

	String getLogPath();
}
