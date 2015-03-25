package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.connectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.connectors.ServerConnector;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;

public interface WorkerConnector extends ServerConnector {
	void sendTask(String workerId, WorkerTask task);
}
