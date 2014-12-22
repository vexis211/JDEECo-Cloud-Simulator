package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.connectors.WorkerConnector;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.control.UpdateExecutionsCommand;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.SimulationManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.WorkerManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning.SimulationScheduler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;

public class JobManagerEngineImpl implements JobManagerEngine {

	@Resource
	private JobManagerUpdateQueue jobManagerUpdateQueue;

	@Resource
	private WorkerManager workerManager;

	@Resource
	private SimulationManager simulationManager;

	@Resource
	private SimulationScheduler simulationScheduler;

	@Resource
	private WorkerConnector workerConnector;
	
	@Override
	public void start() {

		simulationManager.refreshExecutions();
		workerConnector.connect();
		
		while (true) {
			List<JobManagerUpdate> updates = getNewUpdates();
			applyUpdates(updates);
			simulationScheduler.reschedule();
			sendUpdatesToWorkers();
		}
	}

	private List<JobManagerUpdate> getNewUpdates() {
		List<JobManagerUpdate> updates;
		try {
			updates = jobManagerUpdateQueue.takeAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
			updates = new ArrayList<JobManagerUpdate>();
		}
		return updates;
	}

	private void applyUpdates(List<JobManagerUpdate> updates) {

		// updates
		// update worker manager
		Stream<WorkerStatusUpdate> workerStatusUpdates = takeUpdates(updates, WorkerStatusUpdate.class).map(
				x -> (WorkerStatusUpdate) x);
		workerManager.update(workerStatusUpdates);

		// update simulation manager
		Stream<SimulationStatusUpdate> simulationStatusUpdates = takeUpdates(updates, SimulationStatusUpdate.class)
				.map(x -> (SimulationStatusUpdate) x);
		simulationManager.update(simulationStatusUpdates);

		// commands
		// are there any other updates?
		Stream<UpdateExecutionsCommand> updateExecutionsCommands = takeUpdates(updates, UpdateExecutionsCommand.class)
				.map(x -> (UpdateExecutionsCommand) x);
		if (updateExecutionsCommands.findFirst().isPresent())
			simulationManager.refreshExecutions();

		if (!updates.isEmpty()) {
			throw new UnknownJobManagerUpdateException("Unknown job manager updates: " + updates);
		}
	}

	private <T extends JobManagerUpdate> Stream<JobManagerUpdate> takeUpdates(List<JobManagerUpdate> updates,
			Class<T> type) {
		Stream<JobManagerUpdate> taken = updates.stream().filter(x -> x.getClass().equals(type));
		taken.forEach(x -> updates.remove(x));
		return taken;
	}

	private void sendUpdatesToWorkers() {
		// TODO Auto-generated method stub
		
	}
}
