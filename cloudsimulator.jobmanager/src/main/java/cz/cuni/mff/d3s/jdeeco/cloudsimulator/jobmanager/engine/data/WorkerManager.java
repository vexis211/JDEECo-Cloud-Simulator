package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.stream.Stream;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;

public interface WorkerManager {
	void update(Stream<WorkerStatusUpdate> updates);
}
