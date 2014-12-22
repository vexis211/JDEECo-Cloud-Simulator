package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

public class WorkerStatusImpl implements WorkerStatus {

	private static final long serialVersionUID = -757638661771912962L;

	private WorkerState state;

	public WorkerStatusImpl(WorkerState state) {
		this.state = state;
	}

	public WorkerState getState() {
		return state;
	}
}
