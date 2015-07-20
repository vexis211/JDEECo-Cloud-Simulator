package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecutionStatistic;

public interface SimulationExecutionStatisticDao extends BaseDao<SimulationExecutionStatistic> {

	SimulationExecutionStatistic find(int executionId, String name);
}