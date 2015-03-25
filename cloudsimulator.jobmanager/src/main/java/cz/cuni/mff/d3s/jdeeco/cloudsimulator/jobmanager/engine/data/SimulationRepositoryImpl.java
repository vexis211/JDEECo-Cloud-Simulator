package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.SimulationExecutionDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;

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

	@Override
	public void markExecutionAsStarted(int executionId) {
		SimulationExecution execution = simulationExecutionDao.findById(executionId);
		execution.setStarted(new Date());
		simulationExecutionDao.saveOrUpdate(execution);
	}

	@Override
	public void markExecutionAsCompleted(int executionId) {
		SimulationExecution execution = simulationExecutionDao.findById(executionId);
		execution.setEnded(new Date());
		simulationExecutionDao.saveOrUpdate(execution);
	}
}
