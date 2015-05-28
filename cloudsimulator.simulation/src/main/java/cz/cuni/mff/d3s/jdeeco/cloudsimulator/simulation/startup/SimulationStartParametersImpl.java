package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup;

public class SimulationStartParametersImpl implements SimulationStartParameters {

	private Object rootComponent;

	public SimulationStartParametersImpl(Object rootComponent) {
		this.rootComponent = rootComponent;
	}

	@Override
	public Object getRootComponent() {
		return rootComponent;
	}
}
