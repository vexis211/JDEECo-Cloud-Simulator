package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

public interface SimulationExecutionStatistics {
	int getCompletedSimulationsCount();
	long getAverageSimulationTimeInMillis();
	long getSimulationsTimeSumInMillis();

	void simulationStarted(int simulationRunId);
	void simulationCompleted(int simulationRunId);
}
