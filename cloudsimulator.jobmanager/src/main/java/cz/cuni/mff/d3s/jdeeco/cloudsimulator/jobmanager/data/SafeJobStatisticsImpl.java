package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

public class SafeJobStatisticsImpl<T> extends JobStatisticsImpl<T> {

	@Override
	public void jobCompleted(T jobId) {
		if (startedJobIds.containsKey(jobId)) {
			super.jobCompleted(jobId);
		}
	}
}
