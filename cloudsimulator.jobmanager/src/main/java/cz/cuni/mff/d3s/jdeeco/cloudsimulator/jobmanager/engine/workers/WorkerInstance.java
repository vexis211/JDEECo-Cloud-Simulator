package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.cloud.CloudMachine;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.WorkerStatus;

public interface WorkerInstance {

	String getWorkerId();
	
	WorkerStatus getStatus();
	void setStatus(WorkerStatus workerStatus);
	DateTime getLastStatusChange();

	CloudMachine getCloudMachine();
}
