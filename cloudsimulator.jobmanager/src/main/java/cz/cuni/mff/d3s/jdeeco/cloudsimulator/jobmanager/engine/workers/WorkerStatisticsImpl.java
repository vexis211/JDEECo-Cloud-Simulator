package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers;

import java.util.HashMap;

import org.joda.time.DateTime;

public class WorkerStatisticsImpl implements WorkerStatistics {

	private final HashMap<String, DateTime> startingWorkerIds = new HashMap<>();

	private int startTimeMeasurementCount;
	private long startTimesSumInMillis;

	@Override
	public int getStartTimeMeasurementCount() {
		return startTimeMeasurementCount;
	}

	@Override
	public long getAverageStartTimeInMillis() {
		return startTimeMeasurementCount == 0 ? 0 : startTimesSumInMillis / startTimeMeasurementCount;
	}

	@Override
	public long getStartTimesSumInMillis() {
		return startTimesSumInMillis;
	}

	@Override
	public void starting(String workerId) {
		startingWorkerIds.put(workerId, DateTime.now());
	}

	@Override
	public void started(String workerId) {
		DateTime startTime = startingWorkerIds.remove(workerId);
		long diffInMillis = DateTime.now().getMillis() - startTime.getMillis();
		startTimesSumInMillis += diffInMillis;

		startTimeMeasurementCount++;
	}
}
