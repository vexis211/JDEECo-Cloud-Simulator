package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

public interface JobStatistics<T> {
	int getCompletedJobsCount();
	long getAverageJobTimeInMillis();
	long getJobTimeSumInMillis();

	void jobStarted(T jobId);
	void jobCompleted(T jobId);
}
