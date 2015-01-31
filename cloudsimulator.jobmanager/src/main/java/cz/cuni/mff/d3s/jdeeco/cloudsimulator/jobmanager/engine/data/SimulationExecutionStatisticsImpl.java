package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.HashMap;

import org.joda.time.DateTime;

public class SimulationExecutionStatisticsImpl implements SimulationExecutionStatistics {

	private final HashMap<Integer, DateTime> startedSimulationRunIds = new HashMap<>();

	private int completedSimulationsCount;
	private long simulationsTimeSumInMillis;

	@Override
	public int getCompletedSimulationsCount() {
		return completedSimulationsCount;
	}

	@Override
	public long getAverageSimulationTimeInMillis() {
		return completedSimulationsCount == 0 ? 0 : simulationsTimeSumInMillis / completedSimulationsCount;
	}

	@Override
	public long getSimulationsTimeSumInMillis() {
		return simulationsTimeSumInMillis;
	}

	@Override
	public void simulationStarted(int simulationRunId) {
		startedSimulationRunIds.put(simulationRunId, DateTime.now());
	}

	@Override
	public void simulationCompleted(int simulationRunId) {
		DateTime startTime = startedSimulationRunIds.remove(simulationRunId);
		long diffInMillis = DateTime.now().getMillis() - startTime.getMillis();
		simulationsTimeSumInMillis += diffInMillis;

		completedSimulationsCount++;
	}
}
