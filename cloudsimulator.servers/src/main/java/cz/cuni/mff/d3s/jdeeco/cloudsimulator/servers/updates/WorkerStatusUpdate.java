package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

public interface WorkerStatusUpdate extends WorkerUpdate {
	WorkerStatus getWorkerStatus();
}
