package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

public interface SimulationExecutionEntryListener {

	void executionStarted(SimulationExecutionEntry executionEntry);

	void executionCompleted(SimulationExecutionEntry executionEntry);
}
