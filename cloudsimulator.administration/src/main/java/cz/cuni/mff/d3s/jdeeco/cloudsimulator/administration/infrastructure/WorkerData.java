package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure;

import org.joda.time.DateTime;

public interface WorkerData {
	String getName();
	WorkerPowerState getPowerState();

	DateTime getCreatedAt();
	DateTime getLaunchedAt();
}