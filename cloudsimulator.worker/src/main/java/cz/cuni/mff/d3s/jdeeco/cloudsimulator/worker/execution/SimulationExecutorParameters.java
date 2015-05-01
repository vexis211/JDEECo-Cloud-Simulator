package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data.SimulationData;

public interface SimulationExecutorParameters {

	SimulationId getSimulationId();
	SimulationData getSimulationData();
	
	String getRunExecutionDirectory();
	String getRunLogsPath();
	String getRunResultsPath();
	String getStartupFile();
}
