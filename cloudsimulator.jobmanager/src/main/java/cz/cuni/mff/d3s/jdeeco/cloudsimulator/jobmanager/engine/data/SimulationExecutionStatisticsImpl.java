package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.HashMap;

import org.joda.time.DateTime;

public class SimulationExecutionStatisticsImpl implements SimulationExecutionStatistics {

	private final Object locker = new Object();
	private final HashMap<Integer, DateTime> startedSimulationRunIds = new HashMap<>();

	private int completedSimulationsCount;
	private long simulationsTimeSumInMillis;

	@Override
	public int getCompletedSimulationsCount() {
		synchronized (locker) {
			return completedSimulationsCount;
		}
	}

	@Override
	public long getAverageSimulationTimeInMillis() {
		synchronized (locker) {
			return completedSimulationsCount == 0 ? 0 : simulationsTimeSumInMillis / completedSimulationsCount;
		}
	}

	@Override
	public long getSimulationsTimeSumInMillis() {
		synchronized (locker) {
			return simulationsTimeSumInMillis;
		}
	}

	@Override
	public void simulationStarted(int simulationRunId) {
		synchronized (locker) {
			startedSimulationRunIds.put(simulationRunId, DateTime.now());
		}
	}

	@Override
	public void simulationCompleted(int simulationRunId) {
		synchronized (locker) {
			DateTime startTime = startedSimulationRunIds.remove(simulationRunId);
			long diffInMillis = DateTime.now().getMillis() - startTime.getMillis();
			simulationsTimeSumInMillis += diffInMillis;

			completedSimulationsCount++;
		}
	}
}
