package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

public interface SimulationExecutionStatisticItem {

	int getId();
	String getName();
	String getDataType();

	SimulationExecutionItem getSimulationExecution();
	void setSimulationExecution(SimulationExecutionItem execution);
}