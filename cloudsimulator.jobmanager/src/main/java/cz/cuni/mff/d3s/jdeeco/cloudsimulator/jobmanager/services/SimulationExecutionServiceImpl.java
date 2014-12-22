package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.SimulationConfigurationDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.SimulationExecutionDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;

public class SimulationExecutionServiceImpl implements SimulationExecutionService {

	@Resource
	private SimulationConfigurationDao simulationConfigurationDao;

	@Resource
	private SimulationExecutionDao simulationExecutionDao;


	@Override
	public List<SimulationExecution> listExecutions() {
		return simulationExecutionDao.findAll();
	}

	@Override
	public SimulationExecution getExecutionById(int executionId) {
		return simulationExecutionDao.findById(executionId);
	}

	@Transactional(readOnly = false)
	@Override
	public SimulationExecution executeConfiguration(int configurationId, String description) {

		User currentUser = UserHelper.getAuthenticatedUser();
		SimulationConfiguration configuration = simulationConfigurationDao.findById(configurationId);
		
		SimulationExecution execution = new SimulationExecution();
		execution.setCreator(currentUser);
		execution.setSimulationConfiguration(configuration);
		execution.setDescription(description);
		execution.setStatus(SimulationStatus.Created);
		
		simulationExecutionDao.saveOrUpdate(execution);
		
		// TODO enqueue to execution list
		
		return execution;
	}
}
