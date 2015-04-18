package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud.CloudMachine;

public interface WorkerInstance {

	String getWorkerId();
	
	WorkerStatus getStatus();
	void setStatus(WorkerStatus workerStatus);
	DateTime getLastStatusChange();

	CloudMachine getCloudMachine();
}
