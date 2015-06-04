package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

import java.util.EnumSet;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.data.AssertAction;

public interface AssertConfiguration {
	EnumSet<AssertAction> getAssertActions(String groupId);
}
