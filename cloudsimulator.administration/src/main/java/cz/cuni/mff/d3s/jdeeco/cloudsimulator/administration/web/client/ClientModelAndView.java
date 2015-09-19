package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.UserHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.ViewParameters;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.NavigationPath;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.ProjectItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.ProjectListVisibilitySettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationConfigurationItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationDataItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionStatisticItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;

public class ClientModelAndView extends ModelAndView {

	public ClientModelAndView(String viewName) {
		super(viewName);

		User user = UserHelper.getAuthenticatedUser();
		this.addObject("user", user);
	}

	public ClientModelAndView addObject(String attributeName, Object attributeValue) {
		super.addObject(attributeName, attributeValue);
		return this;
	}

	public ClientModelAndView withCancelUri(String cancelUri) {
		return this.addObject(ViewParameters.CANCEL_URI, cancelUri);
	}
	
	public ClientModelAndView withNavigationPath(NavigationPath navigationPath) {
		return this.addObject(ViewParameters.NAVIGATION_PATH, navigationPath);
	}

	public ClientModelAndView withErrorMessage(String errorMessage) {
		return this.addObject(ViewParameters.ERROR_MSG_VAR, errorMessage);
	}

	public ClientModelAndView withProjects(List<ProjectItem> projectItems) {
		return this.addObject(ViewParameters.PROJECTS, projectItems);
	}

	public ClientModelAndView withProject(ProjectItem projectItem) {
		return this.addObject(ViewParameters.PROJECT, projectItem);
	}

	public ClientModelAndView withProjectId(int projectId) {
		return this.addObject(ViewParameters.PROJECT_ID, projectId);
	}

	public ClientModelAndView withProjectListVisibility(ProjectListVisibilitySettings listVisibilitySettings) {
		return this.addObject("projectListVisibilitySettings", listVisibilitySettings);
	}

	public ClientModelAndView withSimulationConfiguration(SimulationConfigurationItem simulationConfigurationItem) {
		return this.addObject(ViewParameters.SIMULATION_CONFIGURATION, simulationConfigurationItem);
	}

	public ClientModelAndView withSimulationConfigurationId(int simulationConfigurationId) {
		return this.addObject(ViewParameters.SIMULATION_CONFIGURATION_ID, simulationConfigurationId);
	}

	public ClientModelAndView withSimulationData(SimulationDataItem simulationDataItem) {
		return this.addObject(ViewParameters.SIMULATION_DATA, simulationDataItem);
	}

	public ClientModelAndView withSimulationDataId(int simulationDataId) {
		return this.addObject(ViewParameters.SIMULATION_DATA_ID, simulationDataId);
	}

	public ClientModelAndView withSimulationExecution(SimulationExecutionItem simulationExecutionItem) {
		return this.addObject(ViewParameters.SIMULATION_EXECUTION, simulationExecutionItem);
	}

	public ClientModelAndView withSimulationExecutions(List<SimulationExecutionItem> simulationExecutionItems) {
		return this.addObject(ViewParameters.SIMULATION_EXECUTIONS, simulationExecutionItems);
	}

	public ClientModelAndView withSimulationExecutionId(int simulationExecutionId) {
		return this.addObject(ViewParameters.SIMULATION_EXECUTION_ID, simulationExecutionId);
	}
	
	public ClientModelAndView withSimulationExecutionStatistics(List<SimulationExecutionStatisticItem> simulationExecutionStatisticItems) {
		return this.addObject(ViewParameters.EXECUTION_STATISTICS, simulationExecutionStatisticItems);
	}
}
