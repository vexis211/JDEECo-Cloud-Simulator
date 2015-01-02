package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.WorkerStatus;

public interface WorkerStatusUpdate extends WorkerUpdate {
	WorkerStatus getWorkerStatus();
}
