package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution.ExecutionListener;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution.SimulationExecutor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution.SimulationExecutorFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution.SimulationExecutorImpl;

public class SimulationManagerImpl implements SimulationManager, ExecutionListener {

	@Resource
	private SimulationExecutorFactory simulationExecutorFactory;
	
	private final List<SimulationExecutor> runningExecutors = new ArrayList<SimulationExecutor>();
	
	@Override
	public void runSimulation(RunSimulationTask task) {
		
		SimulationExecutor executor = simulationExecutorFactory.create(this, , logPath);
		runningExecutors.add(executor);
	}

	@Override
	public void stopAndCleanSimulations() {
		runningExecutors.forEach(x -> x.stop());
		runningExecutors.clear();
		
		
	}

	
	@Override
	public void executionEnded(SimulationExecutor simulationExecutor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executionStopped(SimulationExecutor simulationExecutor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exceptionOccured(SimulationExecutor simulationExecutor) {
		// TODO Auto-generated method stub
		
	}

}
