package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import java.util.List;
import java.util.Set;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRun;
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

	void loadRuns(Set<SimulationRun> simulationRuns);

	boolean isPackagePrepared();
	void setIsPackagePrepared();
	
	void stop();
}
