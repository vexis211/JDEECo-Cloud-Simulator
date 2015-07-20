package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationExecutionEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationRunEntry;

public interface StatisticsProvider {	
	ExecutionStatistics getStatistics(SimulationExecutionEntry execution);
	RunStatistics getStatistics(SimulationRunEntry run);
}
