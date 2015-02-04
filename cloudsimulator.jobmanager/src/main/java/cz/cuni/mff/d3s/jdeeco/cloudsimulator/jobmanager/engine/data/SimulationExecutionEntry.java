package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public interface SimulationExecutionEntry {

	int getId();
	SimulationStatus getStatus();

	boolean containsSimulationRun(int simulationRunId);
	
	int getNotStartedRunsCount();
	List<SimulationRunEntry> getNotStartedRuns();

	void startSimulationRun(SimulationRunEntry simulationRunEntry);
	void updateRunStatus(SimulationStatusUpdate update);
	
	SimulationExecutionStatistics getStatistics();
	ExecutionDeadlineSettings getDeadlineSettings();

	String getPackageName();
	void setPackageName(String packageName);
	
	void stop();
}
