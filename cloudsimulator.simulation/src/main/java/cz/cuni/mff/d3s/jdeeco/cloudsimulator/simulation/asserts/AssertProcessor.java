package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

public interface AssertProcessor {
	String getAssertGroup();	

	void processSuccess(String message);
	void processFail(String message);
}
