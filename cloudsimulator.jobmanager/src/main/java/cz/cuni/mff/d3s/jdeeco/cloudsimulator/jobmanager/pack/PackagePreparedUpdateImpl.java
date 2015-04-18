package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

public class PackagePreparedUpdateImpl implements PackagePreparedUpdate {

	private static final long serialVersionUID = -4205471535823206102L;

	private final int executionId;

	public PackagePreparedUpdateImpl(int executionId) {
		this.executionId = executionId;
	}

	@Override
	public int getExecutionId() {
		return executionId;
	}
}
