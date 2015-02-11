package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.ProjectDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.SimulationConfigurationDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;

public class SimulationConfigurationServiceImpl implements SimulationConfigurationService {

	@Resource
	private SimulationConfigurationDao simulationConfigurationDao;
	
	@Resource
	private ProjectDao projectDao;
	
	@Override
	public List<SimulationConfiguration> listConfigurations() {
		return simulationConfigurationDao.findAll();
	}
	
	@Override
	public List<SimulationConfiguration> listProjectConfigurations(int projectId) {
		return simulationConfigurationDao.findByProjectId(projectId);
	}

	@Override
	public SimulationConfiguration getConfigurationById(int configurationId) {
		return simulationConfigurationDao.findById(configurationId);
	}

	@Transactional(readOnly = false)
	@Override
	public SimulationConfiguration createConfiguration(int projectId, String name, String description) {

		User currentUser = UserHelper.getAuthenticatedUser();
		Project project = projectDao.findById(projectId);
		
		SimulationConfiguration configuration = new SimulationConfiguration(project, currentUser, name, description);
		
		simulationConfigurationDao.saveOrUpdate(configuration);
		
		return configuration;
	}

	@Transactional(readOnly = false)
	@Override
	public void editConfiguration(int configurationId, String name, String description) {

		SimulationConfiguration configuration = simulationConfigurationDao.findById(configurationId);
		configuration.setName(name);
		configuration.setDescription(description);
		
		simulationConfigurationDao.saveOrUpdate(configuration);
	}
}
