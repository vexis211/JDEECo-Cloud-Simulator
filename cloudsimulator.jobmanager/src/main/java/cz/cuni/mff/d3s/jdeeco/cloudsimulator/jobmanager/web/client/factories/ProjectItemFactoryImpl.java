package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectItemImpl;

public class ProjectItemFactoryImpl implements ProjectItemFactory {

	@Resource
	private ConfigurationItemFactory configurationItemFactory;

	@Override
	public ProjectItem create(Project project, boolean addConfigurations) {

		ProjectItem newProjItem = new ProjectItemImpl(project);

		if (addConfigurations){
			project.getSimulationConfigurations().stream().sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
					.forEach(c -> newProjItem.addConfiguration(configurationItemFactory.create(c, false)));
		}
		return newProjItem;
	}
}
