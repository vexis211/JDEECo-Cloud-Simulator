package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

public class PackagePreparedUpdateImpl implements PackagePreparedUpdate {

	private static final long serialVersionUID = -4205471535823206102L;

	private final int executionId;
	private final String packageName;

	public PackagePreparedUpdateImpl(int executionId, String packageName) {
		this.executionId = executionId;
		this.packageName = packageName;
	}

	@Override
	public int getExecutionId() {
		return executionId;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}
}
