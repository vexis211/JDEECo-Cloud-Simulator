package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.PathEx;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class SimulationDataManagerImpl implements SimulationDataManager {

	private final List<Future<?>> runningFutures = new ArrayList<>();

	private final String executionsRootDirectory;
	private final FutureExecutor executor;
	private final SimulationDataListener listener;

	private String resultsRootDirectory;
	private String logsRootDirectory;

	private SimulationDataRepository simulationDataRepository;


	public SimulationDataManagerImpl(String executionsRootDirectory, String resultsRootDirectory, String logsRootDirectory, FutureExecutor executor,
			SimulationDataRepository dataRepository, SimulationDataListener listener) {
		this.executionsRootDirectory = executionsRootDirectory;
		this.resultsRootDirectory = resultsRootDirectory;
		this.logsRootDirectory = logsRootDirectory;

		this.executor = executor;
		this.simulationDataRepository = dataRepository;

		this.listener = listener;
	}

	private void startFuture(Runnable command) {
		Future<?> future = executor.executeWithFuture(command);
		synchronized (runningFutures) {
			runningFutures.removeIf(x -> x.isDone() || x.isCancelled());
			runningFutures.add(future);
		}
	}

	@Override
	public void prepareData(int simulationRunId) {
		startFuture(() -> prepareDataCore(simulationRunId));
	}

	private void prepareDataCore(int simulationRunId) {

		String executionPath = PathEx.combine(executionsRootDirectory, simulationRunId);
		String resultsPath = PathEx.combine(resultsRootDirectory, simulationRunId);
		String logsPath = PathEx.combine(logsRootDirectory, simulationRunId);
		SimulationData preparedData = new SimulationDataImpl(executionPath, resultsPath, logsPath);

		try {
			String packageDir = simulationDataRepository.getPackagePath(String.valueOf(simulationRunId)); // simulation run ID is used like data name
			FileUtils.copyDirectory(new File(packageDir), new File(executionPath));

			listener.dataPrepared(simulationRunId, preparedData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveResults(int simulationRunId, SimulationData data) {
		startFuture(() -> saveResultsCore(simulationRunId, data));
	}

	private void saveResultsCore(int simulationRunId, SimulationData data) {
		simulationDataRepository.saveResults(data, String.valueOf(simulationRunId)); // simulation run ID is used like target name
		listener.resultsSaved(simulationRunId);
	}

	@Override
	public void Dispose() {
		synchronized (runningFutures) {
			for (Future<?> future : runningFutures) {
				future.cancel(true);
			}
			runningFutures.clear();
		}
	}
}
