package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers;

public interface WorkerStatistics {

	void starting(String workerId);
	void started(String workerId);
	int getStartTimeMeasurementCount();
	long getAverageStartTimeInMillis();
	long getStartTimesSumInMillis();
}
