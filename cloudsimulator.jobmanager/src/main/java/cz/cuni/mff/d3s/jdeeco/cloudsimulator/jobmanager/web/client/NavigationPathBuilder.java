package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.NavigationPath;

public interface NavigationPathBuilder {
	NavigationPath buildFromHome();
	NavigationPath buildFromProject(int projectId);
	NavigationPath buildFromSimulationConfiguration(int configurationId);
	NavigationPath buildFromSimulationData(int dataId);
	NavigationPath buildFromSimulationExecution(int executionId);
}
