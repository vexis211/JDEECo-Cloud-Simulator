package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.FutureExecutor;

public class AsyncSimulationExecutorDecorator implements SimulationExecutor {

	private final SimulationExecutor simulationExecutor;
	private Future<?> future;
	private FutureExecutor futureExecutor;

	public AsyncSimulationExecutorDecorator(SimulationExecutor simulationExecutor, FutureExecutor futureExecutor) {
		this.simulationExecutor = simulationExecutor;
		this.futureExecutor = futureExecutor;
	}


	@Override
	public SimulationExecutorParameters getParameters() {
		return simulationExecutor.getParameters();
	}
	
	@Override
	public void start() {
		if (future != null) return;

		Runnable startInternalRunnable = () -> this.simulationExecutor.start();
		this.future = this.futureExecutor.executeWithFuture(startInternalRunnable);
	}

	@Override
	public void stop() {
		simulationExecutor.stop();
		try {
			future.get(); // wait for end
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
