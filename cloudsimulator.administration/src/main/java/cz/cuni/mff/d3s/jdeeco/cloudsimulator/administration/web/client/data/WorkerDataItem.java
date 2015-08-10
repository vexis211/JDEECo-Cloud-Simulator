package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.Date;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure.WorkerPowerState;

public interface WorkerDataItem {
	String getName();
	WorkerPowerState getPowerState();

	Date getCreatedAt();
	Date getLaunchedAt();
}
