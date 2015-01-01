package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public interface FutureExecutor extends Executor {
	Future<?> executeWithFuture(Runnable command);

	<T> Future<T> executeWithFuture(Callable<T> command);
}
