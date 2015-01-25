package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;

public interface SimulationRepository {
	List<SimulationExecution> listNotCompletedExecution();
}
