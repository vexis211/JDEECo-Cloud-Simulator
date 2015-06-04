package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

public interface AssertManager {
	AssertProcessor getProcessor(String assertGroup);
}
