package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public class SimulationManagerImpl implements SimulationManager {

	private final HashMap<Integer, SimulationExecutionEntry> simulationExecutions = new HashMap<>();

	private SimulationRepository simulationRepository;
	private SimulationExecutionEntryFactory simulationExecutionEntryFactory;

	public SimulationManagerImpl(SimulationRepository simulationRepository,
			SimulationExecutionEntryFactory simulationExecutionEntryFactory) {
		this.simulationRepository = simulationRepository;
		this.simulationExecutionEntryFactory = simulationExecutionEntryFactory;

		refreshExecutionsInternal();
	}

	@Override
	public List<SimulationExecutionEntry> listExecutions() {
		return simulationExecutions.values().stream().collect(Collectors.toList());
	}

	@Override
	public void update(List<SimulationStatusUpdate> updates) {
		synchronized (simulationExecutions) {
			for (SimulationStatusUpdate update : updates) {
				SimulationExecutionEntry executionEntry = getExecutionEntry(update.getSimulationRunId());
				executionEntry.updateRunStatus(update);
			}
		}
	}

	private SimulationExecutionEntry getExecutionEntry(int simulationRunId) {
		synchronized (simulationExecutions) {
			Optional<SimulationExecutionEntry> entry = simulationExecutions.values().stream()
					.filter(x -> x.containsSimulationRun(simulationRunId)).findAny();
			return entry.get();
		}
	}

	@Override
	public void refreshExecutions() {
		refreshExecutionsInternal();
	}

	private void refreshExecutionsInternal() {
		List<SimulationExecution> listNotCompletedExecution = simulationRepository.listNotCompletedExecution();

		List<SimulationExecution> notCreatedExecutions;
		synchronized (simulationExecutions) {
			notCreatedExecutions = listNotCompletedExecution.stream()
					.filter(x -> !simulationExecutions.containsKey(x.getId())).collect(Collectors.toList());
		}

		List<SimulationExecutionEntry> newExecutionEntries = notCreatedExecutions.stream()
				.map(x -> simulationExecutionEntryFactory.create(x)).collect(Collectors.toList());

		synchronized (simulationExecutions) {
			newExecutionEntries.stream().filter(x -> !simulationExecutions.containsKey(x.getId()))
					.forEach(x -> simulationExecutions.put(x.getId(), x));
		}
	}
}
