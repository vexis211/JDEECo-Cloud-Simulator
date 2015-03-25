package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.ProjectService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.SimulationConfigurationService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.SimulationDataService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.SimulationExecutionService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.NavigationPath;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.NavigationPathImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.NavigationPathStepImpl;

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

	@Resource
	private AppContext appContext;
	
	@Override
	public NavigationPath buildFromHome() {
		NavigationPath navigationPath = new NavigationPathImpl();
		String url = MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.MAIN);
		navigationPath.addStep(new NavigationPathStepImpl("Home", url));
		return navigationPath;
	}

	@Override
	public NavigationPath buildFromProject(int projectId) {
		Project project = projectService.getProjectById(projectId);
		NavigationPath navigationPath = buildFromHome();
		String url = MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.PROJECT_ROOT, projectId);
		navigationPath.addStep(new NavigationPathStepImpl(project.getName(), url));
		return navigationPath;
	}

	@Override
	public NavigationPath buildFromSimulationConfiguration(int configurationId) {
		SimulationConfiguration configuration = simulationConfigurationService.getConfigurationById(configurationId);
		NavigationPath navigationPath = buildFromProject(configuration.getProject().getId());
		String url = MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.CONFIGURATION_ROOT, configurationId);
		navigationPath.addStep(new NavigationPathStepImpl(configuration.getName(), url));
		return navigationPath;
	}

	@Override
	public NavigationPath buildFromSimulationData(int dataId) {
		SimulationData data = simulationDataService.getDataById(dataId);
		NavigationPath navigationPath = buildFromProject(data.getProject().getId());
		String url = MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.DATA_ROOT, dataId);
		navigationPath.addStep(new NavigationPathStepImpl(data.getName(), url));
		return navigationPath;
	}

	@Override
	public NavigationPath buildFromSimulationExecution(int executionId) {
		SimulationExecution execution = simulationExecutionService.getExecutionById(executionId);
		NavigationPath navigationPath = buildFromSimulationConfiguration(execution.getSimulationConfiguration().getId());
		String url = MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.EXECUTION_ROOT, executionId);
		navigationPath.addStep(new NavigationPathStepImpl(dateFormat.format(execution.getCreated()), url));
		return navigationPath;
	}
}
