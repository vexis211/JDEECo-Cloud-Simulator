package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecutionStatistic;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRunStatistic;

public interface SimulationResultService {
	List<SimulationExecutionStatistic> listExecutionStatistics(int executionId);

	SimulationExecutionStatistic getExecutionStatisticById(int executionStatisticId);
	SimulationExecutionStatistic getExecutionStatistic(int executionId, String name);
	
	List<SimulationRunStatistic> getAllRunStatistics(int executionStatisticId);
}
