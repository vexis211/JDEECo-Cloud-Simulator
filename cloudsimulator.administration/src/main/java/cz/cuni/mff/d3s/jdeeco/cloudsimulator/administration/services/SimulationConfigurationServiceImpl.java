package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.ProjectDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.SimulationConfigurationDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.SimulationDataDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.UserHelper;

public class SimulationConfigurationServiceImpl implements SimulationConfigurationService {

	@Resource
	private SimulationConfigurationDao simulationConfigurationDao;

	@Resource
	private ProjectDao projectDao;
	
	@Resource
	private SimulationDataDao simulationDataDao;
	
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
	public SimulationConfiguration createConfiguration(int projectId, int dataId, String name, String description, int defaultRunCount) {

		User currentUser = UserHelper.getAuthenticatedUser();
		Project project = projectDao.findById(projectId);		
		SimulationData data = simulationDataDao.findById(dataId);
		
		SimulationConfiguration configuration = new SimulationConfiguration(project, currentUser, data, name, description, defaultRunCount);
		
		simulationConfigurationDao.saveOrUpdate(configuration);
		
		return configuration;
	}

	@Transactional(readOnly = false)
	@Override
	public void editConfiguration(int configurationId, String name, String description, int defaultRunCount) {

		SimulationConfiguration configuration = simulationConfigurationDao.findById(configurationId);
		configuration.setName(name);
		configuration.setDescription(description);
		configuration.setDefaultRunCount(defaultRunCount);
		
		simulationConfigurationDao.saveOrUpdate(configuration);
	}
}
