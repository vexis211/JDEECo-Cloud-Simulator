package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public interface SimulationExecutionEntry {

	int getId();
	SimulationStatus getStatus();

	boolean containsSimulationRun(int simulationRunId);
	int getNotStartedRunsCount();

	SimulationRunEntry startSimulationRun();
	void updateRunStatus(SimulationStatusUpdate update);
	
	SimulationExecutionStatistics getStatistics();
	ExecutionDeadlineSettings getDeadlineSettings();
}
