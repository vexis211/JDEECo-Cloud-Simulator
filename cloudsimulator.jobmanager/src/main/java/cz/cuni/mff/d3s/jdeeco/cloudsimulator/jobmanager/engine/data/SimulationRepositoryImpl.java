package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.Collection;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.SimulationExecutionDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;

public class SimulationRepositoryImpl implements SimulationRepository {

	private SimulationExecutionDao simulationExecutionDao;

	public SimulationRepositoryImpl(SimulationExecutionDao simulationExecutionDao) {
		this.simulationExecutionDao = simulationExecutionDao;
	}

	@Override
	public List<SimulationExecution> listNotCompletedExecutions() {
		return simulationExecutionDao.findAllNotCompleted();
	}

	@Override
	public List<SimulationExecution> listStoppedExecutions(Collection<Integer> executionIds) {
		return simulationExecutionDao.findStoppedExecutionsWithIds(executionIds);
	}
}
