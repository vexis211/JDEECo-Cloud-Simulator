package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutor extends ThreadPoolExecutor implements FutureExecutor {

	public CustomThreadPoolExecutor(int corePoolSize) {
		super(corePoolSize, 100, 1L, TimeUnit.DAYS, new SynchronousQueue<Runnable>());
	}

	@Override
	public Future<?> executeWithFuture(Runnable command) {
		return submit(command);
	}

	@Override
	public <T> Future<T> executeWithFuture(Callable<T> command) {
		return submit(command);
	}

}
