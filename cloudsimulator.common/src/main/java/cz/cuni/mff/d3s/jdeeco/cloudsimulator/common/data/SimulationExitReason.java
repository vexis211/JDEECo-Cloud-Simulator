package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data;

public enum SimulationExitReason {
	Finished(0), RunExitCalled(1), ExecutionExitCalled(2), ExceptionOccured(3);

	private final int exitValue;

	private SimulationExitReason(int exitValue) {
		this.exitValue = exitValue;
	}

	public int getExitValue() {
		return exitValue;
	}

	public static SimulationExitReason fromInt(int exitValue) {
		switch (exitValue) {
		case 0:
			return Finished;
		case 1:
			return RunExitCalled;
		case 2:
			return ExecutionExitCalled;
		case 3:
			return ExceptionOccured;
		default:
			throw new EnumConstantNotPresentException(SimulationExitReason.class, String.valueOf(exitValue));
		}
	}
}
