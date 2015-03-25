package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.ProjectItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.ProjectItemImpl;

public class ProjectItemFactoryImpl implements ProjectItemFactory {

	@Resource
	private SimulationConfigurationItemFactory configurationItemFactory;
	
	@Resource
	private SimulationDataItemFactory dataItemFactory;

	@Override
	public ProjectItem create(Project project, boolean addConfigurations, boolean addData) {

		ProjectItem newProjItem = new ProjectItemImpl(project);

		if (addConfigurations){
			project.getSimulationConfigurations().stream().sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
					.forEach(c -> newProjItem.addConfiguration(configurationItemFactory.create(c, false)));
		}
		if (addData){
			project.getSimulationDatas().stream().sorted((d1, d2) -> d1.getName().compareToIgnoreCase(d2.getName()))
					.forEach(d -> newProjItem.addData(dataItemFactory.create(d)));
		}
		return newProjItem;
	}
}
