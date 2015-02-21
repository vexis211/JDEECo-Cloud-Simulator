package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.ArrayList;
import java.util.List;

public class NavigationPathImpl implements NavigationPath {

	private List<NavigationPathStep> steps;

	public NavigationPathImpl() {
		this.steps = new ArrayList<>();
	}

	public NavigationPathImpl(List<NavigationPathStep> steps) {
		this.steps = steps;
	}

	public void addStep(NavigationPathStep step) {
		this.steps.add(step);
	}
	
	@Override
	public List<NavigationPathStep> getSteps() {
		return steps;
	}

}
