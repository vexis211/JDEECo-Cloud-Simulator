package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.stream.Stream;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public interface SimulationManager {
	void update(Stream<SimulationStatusUpdate> updates);

	void refreshExecutions();
}
