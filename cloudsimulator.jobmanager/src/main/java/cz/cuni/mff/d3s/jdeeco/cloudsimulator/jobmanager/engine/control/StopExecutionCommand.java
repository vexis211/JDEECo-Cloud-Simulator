package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.control;

public class StopExecutionCommand implements ControlCommand {

	private static final long serialVersionUID = 4202846808273221640L;

	private int executionId;

	public StopExecutionCommand(int executionId) {
		this.executionId = executionId;
	}

	public int getExecutionId() {
		return executionId;
	}
}
