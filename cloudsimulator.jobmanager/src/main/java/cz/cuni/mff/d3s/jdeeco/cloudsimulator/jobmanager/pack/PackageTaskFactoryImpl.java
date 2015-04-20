package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;

public class PackageTaskFactoryImpl implements PackageTaskFactory {

	@Override
	public PackageTask create(SimulationExecution simulationExecution) {

		SimulationConfiguration simulationConfiguration = simulationExecution.getSimulationConfiguration();
		SimulationData simulationData = simulationConfiguration.getSimulationData();
		
		PackageTaskImpl packageTask = new PackageTaskImpl(simulationExecution.getId());
		packageTask.setRepositoryType(simulationData.getVcsType());
		packageTask.setRepositoryRemoteUrl(simulationData.getRepositoryURL());
		packageTask.setPomDirectory(simulationData.getPomDirectory());
		packageTask.setMavenGoals(simulationData.getMavenGoals().split(" "));
		
		return packageTask;
	}
}
