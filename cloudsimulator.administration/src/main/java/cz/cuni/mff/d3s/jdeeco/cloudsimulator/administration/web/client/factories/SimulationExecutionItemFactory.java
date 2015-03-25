package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionItem;

public interface SimulationExecutionItemFactory {

	public SimulationExecutionItem create(SimulationExecution execution);
}
