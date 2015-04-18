package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning;

public interface SimulationSchedulerSettings {
	int getMaximumNumberOfWorkers();

	float getDesiredRunningWorkerCountRatio();
	float getDesiredCreatedWorkerCountRatio();
}
