package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.stream.Stream;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public class SimulationManagerImpl implements SimulationManager {

	@Resource
	private SimulationRunWorkerMapping simulationRunWorkerMapping;
	
	
	@Override
	public void update(Stream<SimulationStatusUpdate> updates) {
		updates.forEach(x -> update(x));
	}

	private void update(SimulationStatusUpdate x) {
		
		int simulationRunId = simulationRunWorkerMapping.getSimulationRunId(x.getWorkerId());
		
		
	}

	@Override
	public void refreshExecutions() {
		// TODO Auto-generated method stub
		
	}

}
