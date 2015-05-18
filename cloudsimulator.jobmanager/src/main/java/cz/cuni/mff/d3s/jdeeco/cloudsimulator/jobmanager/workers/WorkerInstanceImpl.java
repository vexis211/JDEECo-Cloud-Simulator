package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud.CloudMachine;

public class WorkerInstanceImpl implements WorkerInstance {

	private final String workerId;
	private final CloudMachine cloudMachine;

	private WorkerStatus status;
	private DateTime lastStatusChange;

	public WorkerInstanceImpl(CloudMachine cloudMachine) {
		this.cloudMachine = cloudMachine;
		
		this.workerId = cloudMachine.getName();
		this.lastStatusChange = DateTime.now();

		switch (cloudMachine.getStatus()) {
		case Starting:
			this.status = WorkerStatus.Starting;			
			break;
		case Started:
			this.status = WorkerStatus.Started;			
			break;
		default:
			this.status = WorkerStatus.Stopped;
			break;
		}
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
