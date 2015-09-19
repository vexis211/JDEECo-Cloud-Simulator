package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationEndSettings;

public interface SimulationRunConfiguration {
	String getProfileId();
	SimulationEndSettings getEndSettings();
}
