package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning;

public class SimulationSchedulerSettingsImpl implements SimulationSchedulerSettings {

	private final int maximumNumberOfWorkers;
	private final float desiredCreatedWorkerCountRatio;
	private final float desiredRunningWorkerCountRatio;

	public SimulationSchedulerSettingsImpl(int maximumNumberOfWorkers, float desiredCreatedWorkerCountRatio,
			float desiredRunningWorkerCountRatio) {
		this.maximumNumberOfWorkers = maximumNumberOfWorkers;
		this.desiredCreatedWorkerCountRatio = desiredCreatedWorkerCountRatio;
		this.desiredRunningWorkerCountRatio = desiredRunningWorkerCountRatio;
	}

	@Override
	public int getMaximumNumberOfWorkers() {
		return maximumNumberOfWorkers;
	}

	@Override
	public float getDesiredCreatedWorkerCountRatio() {
		return desiredCreatedWorkerCountRatio;
	}

	@Override
	public float getDesiredRunningWorkerCountRatio() {
		return desiredRunningWorkerCountRatio;
	}
}
