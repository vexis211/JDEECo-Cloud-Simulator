package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.cloud.CloudMachine;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.WorkerStatus;

public class WorkerInstanceImpl implements WorkerInstance {

	private final String workerId;
	private final CloudMachine cloudMachine;

	private WorkerStatus status;
	private DateTime lastStatusChange;

	public WorkerInstanceImpl(String workerId, CloudMachine cloudMachine) {
		this.workerId = workerId;
		this.cloudMachine = cloudMachine;

		this.status = WorkerStatus.Stopped;
		this.lastStatusChange = DateTime.now();
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
		this.lastStatusChange = DateTime.now();
	}

	@Override
	public DateTime getLastStatusChange() {
		return lastStatusChange;
	}	

	@Override
	public CloudMachine getCloudMachine() {
		return cloudMachine;
	}

	@Override
	public int hashCode() {
		int result = 31 + ((workerId == null) ? 0 : workerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		WorkerInstanceImpl other = (WorkerInstanceImpl) obj;
		
		if (workerId == null) {
			if (other.workerId != null) return false;
		} else if (!workerId.equals(other.workerId)) {
			return false;
		}
		return true;
	}
}
