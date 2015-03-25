package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.List;

public interface NavigationPath {
	List<NavigationPathStep> getSteps();	
	void addStep(NavigationPathStep step);
}
