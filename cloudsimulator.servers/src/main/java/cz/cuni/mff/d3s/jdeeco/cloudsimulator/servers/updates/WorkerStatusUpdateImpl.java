package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.WorkerStatus;

public class WorkerStatusUpdateImpl extends WorkerUpdateImpl implements WorkerStatusUpdate {

	private static final long serialVersionUID = -757638661771912962L;

	private WorkerStatus workerStatus;

	public WorkerStatusUpdateImpl(String workerId, WorkerStatus workerStatus) {
		super(workerId);
		
		this.workerStatus = workerStatus;
	}

	@Override
	public WorkerStatus getWorkerStatus() {
		return workerStatus;
	}

}
