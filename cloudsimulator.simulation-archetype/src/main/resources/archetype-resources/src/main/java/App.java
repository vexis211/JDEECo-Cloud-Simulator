#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.SimulationBootstrapper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.SimulationBootstrapperImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.SimulationStartParameters;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.SimulationStartParametersImpl;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args) throws Exception
    {
    	SimulationBootstrapper bootstrapper = new SimulationBootstrapperImpl();
    	bootstrapper.initializeLogging();
    	
    	// TODO replace with your component
		Object rootComponent = new HelloWorld("HELLO");
		
		SimulationStartParameters startParameters = new SimulationStartParametersImpl(rootComponent);
		bootstrapper.startSimulation(startParameters);
		
		bootstrapper.dispose();
    }
}
