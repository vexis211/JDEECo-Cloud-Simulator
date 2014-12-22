package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.control;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.JobManagerUpdateQueue;

public class EngineControllerImpl implements EngineController {

	@Resource
	private JobManagerUpdateQueue jobManagerUpdateQueue;
	
	@Override
	public void processNewExecution(int executionId) {
		jobManagerUpdateQueue.add(new ProcessNewExecutionCommand(executionId));
	}
}
