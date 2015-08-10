package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.Date;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure.WorkerData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure.WorkerPowerState;

public class WorkerDataItemImpl implements WorkerDataItem {

	private final String name;
	private final WorkerPowerState powerState;
	private Date createdAt;
	private Date launchedAt;

	public WorkerDataItemImpl(WorkerData workerData) {
		this.name = workerData.getName();
		this.powerState = workerData.getPowerState();
		this.createdAt = workerData.getCreatedAt().toDate();
		this.launchedAt = workerData.getLaunchedAt().toDate();
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
	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public Date getLaunchedAt() {
		return launchedAt;
	}
}
