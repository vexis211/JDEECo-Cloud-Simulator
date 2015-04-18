package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.connectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.connectors.ServerConnector;

public interface JobManagerConnector extends ServerConnector {
	void sendSimulationStatusUpdate(int simulationExecutionId, int simulationRunId, SimulationStatus status);
	void sendSimulationStatusUpdate(int simulationExecutionId, int simulationRunId, Exception e);
	void sendWorkerStatusUpdate(WorkerStatus status);
}
