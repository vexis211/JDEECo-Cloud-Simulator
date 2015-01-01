package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.PathEx;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.FutureExecutor;

public class SimulationDataManagerImpl implements SimulationDataManager {

	private final List<Future<?>> runningFutures = new ArrayList<>();

	private final String dataParentDirectory;
	private final FutureExecutor executor;
	private final SimulationDataListener listener;

	private String logParentDirectory;

	private SimulationDataRepository simulationDataRepository;

	public SimulationDataManagerImpl(String dataParentDirectory, String logParentDirectory, FutureExecutor executor,
			SimulationDataRepository dataRepository, SimulationDataListener listener) {
		this.dataParentDirectory = dataParentDirectory;
		this.logParentDirectory = logParentDirectory;

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
	public void prepareData(int simulationRunId, String sourceUri) {
		startFuture(() -> prepareDataCore(simulationRunId, sourceUri));
	}

	private void prepareDataCore(int simulationRunId, String sourceUri) {

		String executionPath = PathEx.combine(dataParentDirectory, simulationRunId);
		String logPath = PathEx.combine(logParentDirectory, simulationRunId);
		SimulationData preparedData = new SimulationDataImpl(executionPath, logPath);

		try {
			String templateDataDir = simulationDataRepository.getData(sourceUri);
			Files.copy(Paths.get(templateDataDir), Paths.get(executionPath)); // TODO check if this copy correctly

			listener.dataPrepared(simulationRunId, preparedData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveResults(int simulationRunId, SimulationData data, String targetUri, String targetLogsUri) {
		startFuture(() -> saveResultsCore(simulationRunId, data, targetUri, targetLogsUri));
	}

	private void saveResultsCore(int simulationRunId, SimulationData data, String targetUri, String targetLogsUri) {
		simulationDataRepository.saveResults(data, targetUri, targetLogsUri);
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
