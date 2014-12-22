package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.connectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;

public interface WorkerConnector {
	void connect();
	void disconnect();
	
	void sendTask(String machineName, WorkerTask task);
}
