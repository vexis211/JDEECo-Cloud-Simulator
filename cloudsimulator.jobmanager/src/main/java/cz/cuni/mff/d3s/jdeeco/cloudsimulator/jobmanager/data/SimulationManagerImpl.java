package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagePreparedUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.SimplePackageManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatistics;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.StatisticsProvider;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public class SimulationManagerImpl implements SimulationManager, SimulationExecutionEntryListener {

	private final Logger logger = LoggerFactory.getLogger(SimulationManagerImpl.class);

	private final HashMap<Integer, SimulationExecutionEntry> simulationExecutions = new HashMap<>();

	private final SimulationRepository simulationRepository;
	private final SimulationExecutionEntryFactory simulationExecutionEntryFactory;
	private final SimplePackageManager simplePackageManager;
	private final StatisticsProvider statisticsProvider;

	public SimulationManagerImpl(SimulationRepository simulationRepository,
			SimulationExecutionEntryFactory simulationExecutionEntryFactory, SimplePackageManager simplePackageManager,
			StatisticsProvider statisticsProvider) {
		this.simulationRepository = simulationRepository;
		this.simulationExecutionEntryFactory = simulationExecutionEntryFactory;
		this.simplePackageManager = simplePackageManager;
		this.statisticsProvider = statisticsProvider;
	}

	@Override
	public List<SimulationExecutionEntry> listExecutions() {
		return simulationExecutions.values().stream().collect(Collectors.toList());
	}

	@Override
	public void updateStatus(List<SimulationStatusUpdate> updates) {

		Set<SimulationExecutionEntry> executionsToStop = new HashSet<SimulationExecutionEntry>();

		for (SimulationStatusUpdate update : updates) {
			int executionId = update.getSimulationId().getExecutionId();

			if (simulationExecutions.containsKey(executionId)) {
				SimulationExecutionEntry executionEntry = simulationExecutions.get(executionId);
				try {
					if (update.getExitReason() == SimulationExitReason.ExecutionExitCalled
							&& !executionsToStop.contains(executionEntry)) {
						executionsToStop.add(executionEntry);
					}

					executionEntry.updateRunStatus(update);
				} catch (RuntimeException e) {
					logger.error(String.format("Simulation status update failed. %s.", update.getSimulationId()), e);
				}
			} else {
				logger.info("Update for wrong or stopped execution. {}.", update.getSimulationId());
			}
		}

		// stop executions
		for (SimulationExecutionEntry executionEntry : executionsToStop) {
			executionEntry.stop();
		}
	}

	@Override
	public void updatePackagePrepared(List<PackagePreparedUpdate> updates) {
		for (PackagePreparedUpdate update : updates) {
			SimulationExecution executionData = simulationRepository.initializeExecution(update.getExecutionId(),
					update.getVariablesDefinitions());

			SimulationExecutionEntry simulationExecutionEntry = simulationExecutions.get(update.getExecutionId());
			simulationExecutionEntry.loadRuns(executionData.getSimulationRuns());
			simulationExecutionEntry.setIsPackagePrepared();
		}
	}

	@Override
	public void refreshExecutions() {
		refreshExecutionsInternal();
	}

	private void refreshExecutionsInternal() {
		logger.info("Refreshing simulation executions.");

		// check stopped executions
		List<SimulationExecution> stoppedExecutionModels = simulationRepository
				.listStoppedExecutions(simulationExecutions.keySet());
		List<SimulationExecutionEntry> toStopExecutions = stoppedExecutionModels.stream()
				.map(x -> simulationExecutions.get(x.getId())).collect(Collectors.toList());
		for (SimulationExecutionEntry toStopExecution : toStopExecutions) {
			toStopExecution.stop();
			simulationExecutions.remove(toStopExecution.getId());
		}

		// add new executions
		List<SimulationExecution> notCompletedExecutionModels = simulationRepository.listNotCompletedExecutions();
		List<SimulationExecution> notCreatedExecutions = notCompletedExecutionModels.stream()
				.filter(x -> !simulationExecutions.containsKey(x.getId())).collect(Collectors.toList());

		for (SimulationExecution notCreatedExecution : notCreatedExecutions) {
			SimulationExecutionEntry newExecutionEntry = simulationExecutionEntryFactory.create(notCreatedExecution,
					this);
			simulationExecutions.put(newExecutionEntry.getId(), newExecutionEntry);

			if (simplePackageManager.isPackagePrepared(notCreatedExecution)) {
				// if package is prepared, there is no reason to create runs -
				// it must be already done
				newExecutionEntry.setIsPackagePrepared();
			} else {
				simplePackageManager.preparePackage(notCreatedExecution);
			}
		}
	}

	@Override
	public void runStarted(SimulationRunEntry runEntry) {
		simulationRepository.startRun(runEntry.getId());
	}

	@Override
	public void runCompleted(SimulationRunEntry runEntry) {
		RunStatistics runStatistics = statisticsProvider.getStatistics(runEntry);
		simulationRepository.completeRun(runEntry.getId(), runStatistics);
	}

	@Override
	public void runStopped(SimulationRunEntry runEntry) {
		simulationRepository.stopRun(runEntry.getId());
	}

	@Override
	public void executionStarted(SimulationExecutionEntry executionEntry) {
		simulationRepository.startExecution(executionEntry.getId());
	}

	@Override
	public void executionCompleted(SimulationExecutionEntry executionEntry) {
		int executionId = executionEntry.getId();
		simulationExecutions.remove(executionId);

		simulationRepository.completeExecution(executionId);
	}

	@Override
	public void executionStopped(SimulationExecutionEntry executionEntry) {
		simulationRepository.stopExecution(executionEntry.getId());
	}
}
