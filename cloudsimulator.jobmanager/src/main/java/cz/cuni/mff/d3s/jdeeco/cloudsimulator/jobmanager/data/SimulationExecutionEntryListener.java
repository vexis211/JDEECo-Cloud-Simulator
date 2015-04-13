package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

public interface SimulationExecutionEntryListener {

	void runStarted(SimulationRunEntry runEntry);
	void runCompleted(SimulationRunEntry runEntry);
	
	void executionStarted(SimulationExecutionEntry executionEntry);
	void executionCompleted(SimulationExecutionEntry executionEntry);
}
