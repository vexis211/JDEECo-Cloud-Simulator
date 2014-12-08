package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationExecutionItem;

public interface SimulationExecutionItemFactory {

	public SimulationExecutionItem create(SimulationExecution execution, boolean addRuns);
}
