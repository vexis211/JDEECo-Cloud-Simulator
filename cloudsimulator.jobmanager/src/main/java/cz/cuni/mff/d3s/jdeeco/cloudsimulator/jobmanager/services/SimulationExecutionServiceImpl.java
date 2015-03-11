package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.SimulationConfigurationDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.SimulationExecutionDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.ExecutionEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.control.EngineController;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;

public class SimulationExecutionServiceImpl implements SimulationExecutionService {

	private final Logger logger = Logger.getLogger(SimulationExecutionServiceImpl.class);

	@Resource
	private SimulationConfigurationDao simulationConfigurationDao;

	@Resource
	private SimulationExecutionDao simulationExecutionDao;

	@Resource
	private EngineController engineController;
	
	@Override
	public List<SimulationExecution> listExecutions() {
		return simulationExecutionDao.findAll();
	}

	@Override
	public List<SimulationExecution> listAllNotCompletedExecutions() {
		return simulationExecutionDao.findAllNotCompleted();
	}

	@Override
	public SimulationExecution getExecutionById(int executionId) {
		return simulationExecutionDao.findById(executionId);
	}

	@Transactional(readOnly = false)
	@Override
	public SimulationExecution executeConfiguration(int configurationId, String description, Integer executionRunCount,
			ExecutionEndSpecificationType endSpecificationType, Date endDate) {

		User currentUser = UserHelper.getAuthenticatedUser();
		SimulationConfiguration configuration = simulationConfigurationDao.findById(configurationId);

		SimulationExecution execution = new SimulationExecution();
		execution.setCreator(currentUser);
		execution.setSimulationConfiguration(configuration);
		execution.setDescription(description);
		execution.setRunCount(executionRunCount != null ? executionRunCount : configuration.getDefaultRunCount());
		// end specification
		execution.setEndSpecificationType(endSpecificationType);
		if (endSpecificationType == ExecutionEndSpecificationType.ToDate) {
			execution.setEndDate(endDate);
		}

		execution.setStatus(SimulationStatus.Created);

		simulationExecutionDao.saveOrUpdate(execution);

		// notify engine
		engineController.updateExecutions();

		return execution;
	}

	@Transactional(readOnly = false)
	@Override
	public void setDescription(int executionId, String description) {
		SimulationExecution execution = simulationExecutionDao.findById(executionId);
		execution.setDescription(description);

		simulationExecutionDao.saveOrUpdate(execution);
	}

	@Transactional(readOnly = false)
	@Override
	public void stopExecution(int executionId) {
		SimulationExecution execution = simulationExecutionDao.findById(executionId);

		switch (execution.getStatus()) {
		case Created:
		case Started:
			execution.setStatus(SimulationStatus.Stopped);
			simulationExecutionDao.saveOrUpdate(execution);
			logger.info("Stopped execution with id: " + executionId);
			break;
		case Completed:
			logger.info("Tried to stop completed execution with id: " + executionId);
			break;
		default:
			logger.warn(String.format("Tried to stop execution with id: '%s', status: '%s'.", executionId, execution.getStatus()));
			break;
		}

		// notify engine
		engineController.updateExecutions();
	}
}
