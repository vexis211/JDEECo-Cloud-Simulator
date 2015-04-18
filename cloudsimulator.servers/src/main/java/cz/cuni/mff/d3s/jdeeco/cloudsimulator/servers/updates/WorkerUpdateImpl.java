package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

public class WorkerUpdateImpl implements WorkerUpdate {

	private static final long serialVersionUID = 6749608486658104288L;

	private final String workerId;

	public WorkerUpdateImpl(String workerId) {
		this.workerId = workerId;
	}

	@Override
	public String getWorkerId() {
		return workerId;
	}
}
