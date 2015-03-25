package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

public interface SimulationExecutionEntryListener {

	void executionStarted(SimulationExecutionEntry executionEntry);

	void executionCompleted(SimulationExecutionEntry executionEntry);
}
