package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results;

public class ResultsAggregatedUpdateImpl implements ResultsAggregatedUpdate {

	private static final long serialVersionUID = -4205471535823206102L;

	private final int executionId;

	public ResultsAggregatedUpdateImpl(int executionId) {
		this.executionId = executionId;
	}

	@Override
	public int getExecutionId() {
		return executionId;
	}
}
