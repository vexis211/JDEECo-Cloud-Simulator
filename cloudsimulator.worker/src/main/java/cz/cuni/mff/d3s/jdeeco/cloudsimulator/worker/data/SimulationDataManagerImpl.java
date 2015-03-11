package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.PathEx;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

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
	public void prepareData(int simulationRunId, String dataName) {
		startFuture(() -> prepareDataCore(simulationRunId, dataName));
	}

	private void prepareDataCore(int simulationRunId, String dataName) {

		String executionPath = PathEx.combine(dataParentDirectory, simulationRunId);
		String logPath = PathEx.combine(logParentDirectory, simulationRunId);
		SimulationData preparedData = new SimulationDataImpl(executionPath, logPath);

		try {
			String templateDataDir = simulationDataRepository.getData(dataName);
			FileUtils.copyDirectory(new File(templateDataDir), new File(executionPath));

			listener.dataPrepared(simulationRunId, preparedData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveResults(int simulationRunId, SimulationData data, String dataName) {
		startFuture(() -> saveResultsCore(simulationRunId, data, dataName));
	}

	private void saveResultsCore(int simulationRunId, SimulationData data, String dataName) {
		simulationDataRepository.saveResults(data, dataName);
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
