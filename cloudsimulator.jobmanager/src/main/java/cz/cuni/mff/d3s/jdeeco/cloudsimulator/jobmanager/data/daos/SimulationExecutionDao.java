package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import java.util.Collection;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;

public interface SimulationExecutionDao extends BaseDao<SimulationExecution> {

	public List<SimulationExecution> findAllNotCompleted();

	public List<SimulationExecution> findStoppedExecutionsWithIds(Collection<Integer> executionIds);
}