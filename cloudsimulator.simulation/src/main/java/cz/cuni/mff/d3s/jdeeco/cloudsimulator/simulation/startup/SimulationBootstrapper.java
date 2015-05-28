package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup;

import java.io.FileNotFoundException;

import cz.cuni.mff.d3s.deeco.annotations.processor.AnnotationProcessorException;

public interface SimulationBootstrapper {
	void initializeLogging() throws FileNotFoundException;

	void startSimulation(SimulationStartParameters startParameters) throws AnnotationProcessorException;
}
