package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning;

public class SimulationSchedulerSettingsImpl implements SimulationSchedulerSettings {

	private final int maximumNumberOfWorkers;

	public SimulationSchedulerSettingsImpl(int maximumNumberOfWorkers) {
		this.maximumNumberOfWorkers = maximumNumberOfWorkers;
	}

	@Override
	public int getMaximumNumberOfWorkers() {
		return maximumNumberOfWorkers;
	}
}
