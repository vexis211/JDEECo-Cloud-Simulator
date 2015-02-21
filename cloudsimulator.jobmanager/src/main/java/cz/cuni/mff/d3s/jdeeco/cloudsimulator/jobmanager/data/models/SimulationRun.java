package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models;

public class SimulationRun implements java.io.Serializable {
	
	private static final long serialVersionUID = -5788099497333128349L;

	private Integer id;
	private SimulationExecution simulationExecution;

	public SimulationRun() {
	}

	public SimulationRun(SimulationExecution simulationExecution) {
		this.simulationExecution = simulationExecution;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SimulationExecution getSimulationExecution() {
		return this.simulationExecution;
	}

	public void setSimulationExecution(SimulationExecution simulationExecution) {
		this.simulationExecution = simulationExecution;
	}
}
