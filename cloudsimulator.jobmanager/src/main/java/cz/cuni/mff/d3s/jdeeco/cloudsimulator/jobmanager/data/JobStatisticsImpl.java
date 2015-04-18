package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import java.util.HashMap;

import org.joda.time.DateTime;

public class JobStatisticsImpl<T> implements JobStatistics<T> {

	protected final HashMap<T, DateTime> startedJobIds = new HashMap<>();

	private int completedJobsCount;
	private long jobTimeSumInMillis;

	@Override
	public int getCompletedJobsCount() {
		return completedJobsCount;
	}

	@Override
	public long getAverageJobTimeInMillis() {
		return completedJobsCount == 0 ? 0 : jobTimeSumInMillis / completedJobsCount;
	}

	@Override
	public long getJobTimeSumInMillis() {
		return jobTimeSumInMillis;
	}

	@Override
	public void jobStarted(T jobId) {
		startedJobIds.put(jobId, DateTime.now());
	}

	@Override
	public void jobCompleted(T jobId) {
		DateTime startTime = startedJobIds.remove(jobId);
		long diffInMillis = DateTime.now().getMillis() - startTime.getMillis();
		jobTimeSumInMillis += diffInMillis;

		completedJobsCount++;
	}
}
