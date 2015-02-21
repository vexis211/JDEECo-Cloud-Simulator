package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.ProjectService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.SimulationConfigurationService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.SimulationDataService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.SimulationExecutionService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.NavigationPath;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.NavigationPathImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.NavigationPathStepImpl;

public class NavigationPathBuilderImpl implements NavigationPathBuilder {

	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Resource
	private ProjectService projectService;

	@Resource
	private SimulationConfigurationService simulationConfigurationService;

	@Resource
	private SimulationDataService simulationDataService;

	@Resource
	private SimulationExecutionService simulationExecutionService;

	@Override
	public NavigationPath buildFromHome() {
		NavigationPath navigationPath = new NavigationPathImpl();
		navigationPath.addStep(new NavigationPathStepImpl("Home", MappingSettings.MAIN));
		return navigationPath;
	}

	@Override
	public NavigationPath buildFromProject(int projectId) {
		Project project = projectService.getProjectById(projectId);
		NavigationPath navigationPath = buildFromHome();
		navigationPath.addStep(new NavigationPathStepImpl(project.getName(), String.format("%s/%s",
				MappingSettings.PROJECT_ROOT, projectId)));
		return navigationPath;
	}

	@Override
	public NavigationPath buildFromSimulationConfiguration(int configurationId) {
		SimulationConfiguration configuration = simulationConfigurationService.getConfigurationById(configurationId);
		NavigationPath navigationPath = buildFromProject(configuration.getProject().getId());
		navigationPath.addStep(new NavigationPathStepImpl(configuration.getName(), String.format("%s/%s",
				MappingSettings.CONFIGURATION_ROOT, configurationId)));
		return navigationPath;
	}

	@Override
	public NavigationPath buildFromSimulationData(int dataId) {
		SimulationData data = simulationDataService.getDataById(dataId);
		NavigationPath navigationPath = buildFromProject(data.getProject().getId());
		navigationPath.addStep(new NavigationPathStepImpl(data.getName(), String.format("%s/%s",
				MappingSettings.DATA_ROOT, dataId)));
		return navigationPath;
	}

	@Override
	public NavigationPath buildFromSimulationExecution(int executionId) {
		SimulationExecution execution = simulationExecutionService.getExecutionById(executionId);
		NavigationPath navigationPath = buildFromSimulationConfiguration(execution.getSimulationConfiguration().getId());
		navigationPath.addStep(new NavigationPathStepImpl(dateFormat.format(execution.getCreated()), String.format(
				"%s/%s", MappingSettings.EXECUTION_ROOT, executionId)));
		return navigationPath;
	}
}
