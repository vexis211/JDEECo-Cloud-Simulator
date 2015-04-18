package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.WorkerStatus;

public interface WorkerStatusUpdate extends WorkerUpdate {
	WorkerStatus getWorkerStatus();
}
