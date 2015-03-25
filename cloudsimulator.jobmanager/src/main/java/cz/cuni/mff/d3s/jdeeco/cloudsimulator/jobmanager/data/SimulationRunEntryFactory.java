package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRun;

public interface SimulationRunEntryFactory {

	SimulationRunEntry create(SimulationRun data, SimulationExecutionEntry executionEntry);
}
