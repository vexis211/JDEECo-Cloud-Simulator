package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.Collection;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;

public interface SimulationRepository {
	List<SimulationExecution> listNotCompletedExecutions();
	List<SimulationExecution> listStoppedExecutions(Collection<Integer> executionIds);
	
	void markExecutionAsStarted(int executionId);
	void markExecutionAsCompleted(int executionId);
}
