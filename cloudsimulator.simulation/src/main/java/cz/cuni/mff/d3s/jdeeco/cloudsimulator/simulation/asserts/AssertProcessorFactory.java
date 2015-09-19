package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

import java.util.EnumSet;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.AssertAction;

public interface AssertProcessorFactory {
	AssertProcessor create(String assertGroup, EnumSet<AssertAction> assertActions);
}
