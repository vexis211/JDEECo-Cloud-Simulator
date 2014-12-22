package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

public interface WorkerStatus extends WorkerUpdate {
	WorkerState getState();
}
