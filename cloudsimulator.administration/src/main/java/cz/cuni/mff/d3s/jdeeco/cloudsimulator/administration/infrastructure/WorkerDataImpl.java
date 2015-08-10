package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure;

import org.joda.time.DateTime;

public class WorkerDataImpl implements WorkerData {

	private final String name;
	private final WorkerPowerState powerState;
	private final DateTime createdAt;
	private final DateTime launchedAt;

	public WorkerDataImpl(String name, WorkerPowerState powerState, DateTime createdAt, DateTime launchedAt) {
		this.name = name;
		this.powerState = powerState;
		this.createdAt = createdAt;
		this.launchedAt = launchedAt;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public WorkerPowerState getPowerState() {
		return powerState;
	}

	@Override
	public DateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public DateTime getLaunchedAt() {
		return launchedAt;
	}
}
