package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.cloud.CloudMachine;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.WorkerStatus;

public class WorkerInstanceImpl implements WorkerInstance {

	private final String workerId;
	private final CloudMachine cloudMachine;

	private WorkerStatus status;

	public WorkerInstanceImpl(String workerId, CloudMachine cloudMachine) {
		this.workerId = workerId;
		this.cloudMachine = cloudMachine;

		this.status = WorkerStatus.Stopped;
	}

	@Override
	public String getWorkerId() {
		return workerId;
	}

	@Override
	public WorkerStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(WorkerStatus status) {
		this.status = status;
	}

	@Override
	public CloudMachine getCloudMachine() {
		return cloudMachine;
	}
}
