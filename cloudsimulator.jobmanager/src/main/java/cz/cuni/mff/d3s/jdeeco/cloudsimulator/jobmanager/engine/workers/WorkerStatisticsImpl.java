package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers;

import java.util.HashMap;

import org.joda.time.DateTime;

public class WorkerStatisticsImpl implements WorkerStatistics {

	private final Object locker = new Object();
	private final HashMap<String, DateTime> startingWorkerIds = new HashMap<>();

	private int startTimeMeasurementCount;
	private long startTimesSumInMillis;

	@Override
	public int getStartTimeMeasurementCount() {
		synchronized (locker) {
			return startTimeMeasurementCount;
		}
	}

	@Override
	public long getAverageStartTimeInMillis() {
		synchronized (locker) {
			return startTimeMeasurementCount == 0 ? 0 : startTimesSumInMillis / startTimeMeasurementCount;
		}
	}

	@Override
	public long getStartTimesSumInMillis() {
		synchronized (locker) {
			return startTimesSumInMillis;
		}
	}
	
	@Override
	public void starting(String workerId) {
		synchronized (locker) {
			startingWorkerIds.put(workerId, DateTime.now());
		}
	}

	@Override
	public void started(String workerId) {
		synchronized (locker) {
			DateTime startTime = startingWorkerIds.remove(workerId);
			long diffInMillis = DateTime.now().getMillis() - startTime.getMillis();
			startTimesSumInMillis += diffInMillis;

			startTimeMeasurementCount++;
		}		
	}

}
