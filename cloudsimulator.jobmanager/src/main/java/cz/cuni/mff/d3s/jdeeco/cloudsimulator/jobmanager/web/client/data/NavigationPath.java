package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.List;

public interface NavigationPath {
	List<NavigationPathStep> getSteps();	
	void addStep(NavigationPathStep step);
}
