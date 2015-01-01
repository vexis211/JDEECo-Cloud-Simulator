package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.connectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatus;

public interface JobManagerConnector {
	void connect();

	void disconnect();

	void sendSimulationStatusUpdate(int simulationRunId, SimulationStatus status);
	void sendSimulationStatusUpdate(int simulationRunId, Exception e);
	void sendWorkerStatusUpdate(WorkerStatus status);
}
