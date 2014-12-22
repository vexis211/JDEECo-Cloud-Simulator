package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.control;

public class ProcessNewExecutionCommand implements ControlCommand {

	private static final long serialVersionUID = 6349740372643849277L;

	private int executionId;

	public ProcessNewExecutionCommand(int executionId) {
		this.executionId = executionId;
	}

	public int getExecutionId() {
		return executionId;
	}
}
