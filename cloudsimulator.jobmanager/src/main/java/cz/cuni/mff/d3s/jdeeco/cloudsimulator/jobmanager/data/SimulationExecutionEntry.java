package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public interface SimulationExecutionEntry {

	int getId();
	String getStartupFile();
	
	SimulationStatus getStatus();

	int getNotStartedRunsCount();
	List<SimulationRunEntry> getNotStartedRuns();
	List<SimulationRunEntry> getCompletedRuns();

	void startSimulationRun(SimulationRunEntry simulationRunEntry);
	void updateRunStatus(SimulationStatusUpdate update);
	
	JobStatistics<Integer> getExecutionStatistics();
	ExecutionDeadlineSettings getDeadlineSettings();

	boolean isPackagePrepared();
	void setIsPackagePrepared();
	
	void stop();
}
