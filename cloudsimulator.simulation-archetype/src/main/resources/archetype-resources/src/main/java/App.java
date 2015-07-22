#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package {package};

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup.LogInitializer;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup.SimulationBase;

public class App {
	public static void main(String[] args) throws Exception {
		LogInitializer.initialize();

		SimulationBase simulation = new HelloWorldSimulation();
		simulation.start();
	}
}
