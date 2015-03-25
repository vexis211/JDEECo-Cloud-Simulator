package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.ProjectItem;

public interface ProjectItemFactory {
	public ProjectItem create(Project project, boolean addConfigurations, boolean addData);
}
