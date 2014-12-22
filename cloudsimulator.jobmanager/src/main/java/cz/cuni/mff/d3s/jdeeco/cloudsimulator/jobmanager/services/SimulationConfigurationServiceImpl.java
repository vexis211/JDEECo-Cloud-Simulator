package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	private Stream<SimulationConfiguration> streamVisibleConfigurations() {

		User currentUser = UserHelper.getAuthenticatedUser();		
		Stream<SimulationConfiguration> configurations = currentUser.getVisibleSimulationConfigurations()
				.stream();
		
		return configurations;
	}
	
	@Override
	public List<SimulationConfiguration> listVisibleConfigurations() {
		List<SimulationConfiguration> configurations = streamVisibleConfigurations().collect(Collectors.toList());		
		return configurations;
	}

	@Override
	public List<SimulationConfiguration> listHiddenConfigurations() {

		List<SimulationConfiguration> allConfigurations = simulationConfigurationDao.findAll();
		Set<Integer> visibleConfigurationIds = streamVisibleConfigurations()
				.map(x -> x.getId()).collect(Collectors.toSet());
		
		List<SimulationConfiguration> hiddenConfigurations = allConfigurations.stream()
				.filter(x -> !visibleConfigurationIds.contains(x.getId())).collect(Collectors.toList());
		
		return hiddenConfigurations;
	}
	
	@Override
	public List<SimulationConfiguration> listVisibleConfigurations(int projectId) {
		Project project = projectDao.findById(projectId);
		List<SimulationConfiguration> configurations = project.getSimulationConfigurations().stream()
				.filter(x -> x.getProject().getId() == projectId).collect(Collectors.toList());
		return configurations;
	}

	@Override
	public List<SimulationConfiguration> listHiddenConfigurations(int projectId) {

		Project project = projectDao.findById(projectId);
		Set<SimulationConfiguration> allConfigurations = project.getSimulationConfigurations();
		Set<Integer> visibleConfigurationIds = project.getSimulationConfigurations().stream()
				.map(x -> x.getId()).collect(Collectors.toSet());
		
		List<SimulationConfiguration> hiddenConfigurations = allConfigurations.stream()
				.filter(x -> !visibleConfigurationIds.contains(x.getId())).collect(Collectors.toList());
		
		return hiddenConfigurations;
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
