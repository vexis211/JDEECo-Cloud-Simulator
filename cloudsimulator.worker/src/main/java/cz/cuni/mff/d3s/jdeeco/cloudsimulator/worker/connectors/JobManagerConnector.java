package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.connectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;

public interface JobManagerConnector {
	void connect();
	void disconnect();
	
	void sendUpdate(JobManagerUpdate update);
}
