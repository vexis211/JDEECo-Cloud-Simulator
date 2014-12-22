package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

public class AsyncSimulationExecutorDecorator implements SimulationExecutor {

	private final SimulationExecutor executor;
	private Thread thread;
	private boolean isStarted;

	public AsyncSimulationExecutorDecorator(SimulationExecutor executor) {
		this.executor = executor;
	}

	@Override
	public void start() {
		if (isStarted)
			return;
		this.isStarted = true;

		Runnable startInternalRunnable = () -> this.executor.start();
		this.thread = new Thread(startInternalRunnable); // TODO thread pool
		thread.start();
	}

	@Override
	public void stop() {
		executor.stop();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
