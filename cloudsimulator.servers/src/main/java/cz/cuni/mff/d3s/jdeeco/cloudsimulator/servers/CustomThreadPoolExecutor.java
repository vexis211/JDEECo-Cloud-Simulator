package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomThreadPoolExecutor extends ThreadPoolExecutor implements FutureExecutor {

	private static Logger logger = LoggerFactory.getLogger(CustomThreadPoolExecutor.class);

	public CustomThreadPoolExecutor(int corePoolSize) {
		super(corePoolSize, Integer.MAX_VALUE, 1L, TimeUnit.DAYS, new SynchronousQueue<Runnable>());
	}

	@Override
	public Future<?> executeWithFuture(Runnable command) {
		logger.debug("Running new command: '{}'.", command);
		
		return submit(command);
	}

	@Override
	public <T> Future<T> executeWithFuture(Callable<T> command) {
		logger.debug("Running new command: '{}'.", command);
		
		return submit(command);
	}
}
