package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import java.util.Collection;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;

public interface SimulationRepository {
	List<SimulationExecution> listNotCompletedExecutions();
	List<SimulationExecution> listStoppedExecutions(Collection<Integer> executionIds);

	void initializeExecution(SimulationExecution notCreatedExecution);
	
	void markRunAsStarted(int runId);
	void markRunAsCompleted(int runId);
	void markRunAsStopped(int runId);
	
	void markExecutionAsStarted(int executionId);
	void markExecutionAsCompleted(int executionId);
	void markExecutionAsStopped(int executionId);
}
