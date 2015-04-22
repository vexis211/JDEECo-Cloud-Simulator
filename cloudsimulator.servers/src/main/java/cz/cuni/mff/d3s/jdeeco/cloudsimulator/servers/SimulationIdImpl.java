package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

public class SimulationIdImpl implements SimulationId {

	private static final long serialVersionUID = -8715591049695289403L;

	private final int executionId;
	private final int runId;

	public SimulationIdImpl(int executionId, int runId) {
		this.executionId = executionId;
		this.runId = runId;
	}

	@Override
	public int getExecutionId() {
		return executionId;
	}

	@Override
	public int getRunId() {
		return runId;
	}

	@Override
	public int hashCode() {
		int result = 31 + executionId;
		result = 31 * result + runId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimulationIdImpl other = (SimulationIdImpl) obj;
		if (executionId != other.executionId)
			return false;
		if (runId != other.runId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("SimulationIdImpl [executionId=%s, runId=%s]", executionId, runId);
	}
}
