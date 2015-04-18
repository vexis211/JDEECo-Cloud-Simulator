package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.SimulationExecutionDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.SimulationRunDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRun;

public class SimulationRepositoryImpl implements SimulationRepository {

	private SimulationExecutionDao simulationExecutionDao;
	private SimulationRunDao simulationRunDao;

	public SimulationRepositoryImpl(SimulationExecutionDao simulationExecutionDao, SimulationRunDao simulationRunDao) {
		this.simulationExecutionDao = simulationExecutionDao;
		this.simulationRunDao = simulationRunDao;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SimulationExecution> listNotCompletedExecutions() {
		return simulationExecutionDao.findAllNotCompleted();
	}

	@Transactional(readOnly = true)
	@Override
	public List<SimulationExecution> listStoppedExecutions(Collection<Integer> executionIds) {
		if (executionIds.isEmpty()) {
			return Collections.<SimulationExecution> emptyList();
		}
		return simulationExecutionDao.findStoppedExecutionsWithIds(executionIds);
	}

	@Transactional(readOnly = false)
	@Override
	public void initializeExecution(SimulationExecution execution) {
		Set<SimulationRun> simulationRuns = execution.getSimulationRuns();
		int toAddCount = execution.getRunCount() - simulationRuns.size();
		
		if (toAddCount > 0) {
			// TODO do much better
			for (int i = 0; i < toAddCount; i++) {
				SimulationRun newRun = new SimulationRun(execution);
				simulationRuns.add(newRun);
				simulationRunDao.saveOrUpdate(newRun);
			}
			
			execution.setSimulationRuns(simulationRuns);
			simulationExecutionDao.saveOrUpdate(execution);
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void markRunAsStarted(int runId) {
		SimulationRun run = simulationRunDao.findById(runId);
		run.setStarted(new Date());
		run.setStatus(SimulationStatus.Started);
		simulationRunDao.saveOrUpdate(run);
	}

	@Transactional(readOnly = false)
	@Override
	public void markRunAsCompleted(int runId) {
		SimulationRun run = simulationRunDao.findById(runId);
		run.setEnded(new Date());
		run.setStatus(SimulationStatus.Completed);
		simulationRunDao.saveOrUpdate(run);
	}

	@Transactional(readOnly = false)
	@Override
	public void markExecutionAsStarted(int executionId) {
		SimulationExecution execution = simulationExecutionDao.findById(executionId);
		execution.setStarted(new Date());
		execution.setStatus(SimulationStatus.Started);
		simulationExecutionDao.saveOrUpdate(execution);
	}

	@Transactional(readOnly = false)
	@Override
	public void markExecutionAsCompleted(int executionId) {
		SimulationExecution execution = simulationExecutionDao.findById(executionId);
		execution.setEnded(new Date());
		execution.setStatus(SimulationStatus.Completed);
		simulationExecutionDao.saveOrUpdate(execution);
	}
}
