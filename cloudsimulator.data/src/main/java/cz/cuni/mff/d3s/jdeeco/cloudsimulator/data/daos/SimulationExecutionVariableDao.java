package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecutionVariable;

public interface SimulationExecutionVariableDao extends BaseDao<SimulationExecutionVariable> {

	SimulationExecutionVariable find(int executionId, String name);
}