package {package};

import cz.cuni.mff.d3s.deeco.runners.DEECoSimulation;
import cz.cuni.mff.d3s.deeco.runtime.DEECoNode;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup.SimulationBase;
import cz.cuni.mff.d3s.jdeeco.network.device.SimpleBroadcastDevice;
import cz.cuni.mff.d3s.jdeeco.position.PositionPlugin;

public class HelloWorldSimulation extends SimulationBase {

	@Override
	protected DEECoSimulation configureSimulation(String profileId) {
		DEECoSimulation simulation = createSimulationWithDeeco();

		/* create first deeco node */
		DEECoNode deecoNode1 = simulation.createNode(new PositionPlugin(0, SimpleBroadcastDevice.DEFAULT_RANGE));
		/* deploy components and ensembles */
		deecoNode1.deployComponent(new HelloWorld("HELLO"));

		return simulation;
	}
}
