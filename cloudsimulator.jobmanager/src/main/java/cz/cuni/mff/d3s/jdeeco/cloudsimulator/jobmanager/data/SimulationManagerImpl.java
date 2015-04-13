package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagePreparedUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.SimplePackageManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public class SimulationManagerImpl implements SimulationManager, SimulationExecutionEntryListener {

	private final HashMap<Integer, SimulationExecutionEntry> simulationExecutions = new HashMap<>();

	private final SimulationRepository simulationRepository;
	private final SimulationExecutionEntryFactory simulationExecutionEntryFactory;
	private final SimplePackageManager simplePackageManager;

	public SimulationManagerImpl(SimulationRepository simulationRepository,
			SimulationExecutionEntryFactory simulationExecutionEntryFactory, SimplePackageManager simplePackageManager) {
		this.simulationRepository = simulationRepository;
		this.simulationExecutionEntryFactory = simulationExecutionEntryFactory;
		this.simplePackageManager = simplePackageManager;

		refreshExecutionsInternal();
	}

	@Override
	public List<SimulationExecutionEntry> listExecutions() {
		return simulationExecutions.values().stream().collect(Collectors.toList());
	}

	@Override
	public void updateStatus(List<SimulationStatusUpdate> updates) {
		for (SimulationStatusUpdate update : updates) {
			SimulationExecutionEntry executionEntry = getExecutionEntry(update.getSimulationRunId());
			executionEntry.updateRunStatus(update);
		}
	}

	@Override
	public void updatePackageNames(List<PackagePreparedUpdate> updates) {
		for (PackagePreparedUpdate update : updates) {
			SimulationExecutionEntry simulationExecutionEntry = simulationExecutions.get(update.getExecutionId());
			simulationExecutionEntry.setPackageName(update.getPackageName());
		}
	}

	private SimulationExecutionEntry getExecutionEntry(int simulationRunId) {
		Optional<SimulationExecutionEntry> entry = simulationExecutions.values().stream()
				.filter(x -> x.containsSimulationRun(simulationRunId)).findAny();
		return entry.get();
	}

	@Override
	public void refreshExecutions() {
		refreshExecutionsInternal();
	}

	private void refreshExecutionsInternal() {
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
			simulationRepository.initializeExecution(notCreatedExecution);
			SimulationExecutionEntry newExecutionEntry = simulationExecutionEntryFactory.create(notCreatedExecution,
					this);
			simulationExecutions.put(newExecutionEntry.getId(), newExecutionEntry);

			String packageName = simplePackageManager.getPackageName(notCreatedExecution);
			if (packageName != null) {
				newExecutionEntry.setPackageName(packageName);
			} else {
				simplePackageManager.preparePackage(notCreatedExecution);
			}
		}
	}

	@Override
	public void runStarted(SimulationRunEntry runEntry) {
		simulationRepository.markRunAsStarted(runEntry.getId());
	}

	@Override
	public void runCompleted(SimulationRunEntry runEntry) {
		simulationRepository.markRunAsCompleted(runEntry.getId());
	}

	@Override
	public void executionStarted(SimulationExecutionEntry executionEntry) {
		simulationRepository.markExecutionAsStarted(executionEntry.getId());
	}

	@Override
	public void executionCompleted(SimulationExecutionEntry executionEntry) {
		simulationRepository.markExecutionAsCompleted(executionEntry.getId());
	}
}
