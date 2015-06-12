package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import java.io.IOException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("SimulationRunProfile")
public class SimulationRunSettingsProfile {

	@XStreamAsAttribute
	@XStreamAlias("Id")
	private final String id;

	@XStreamAlias("SimulationEnd")
	private final SimulationEndSettings simulationEndSettings;
	
	public SimulationRunSettingsProfile(String id, SimulationEndSettings simulationEndSettings) {
		this.id = id;
		this.simulationEndSettings = simulationEndSettings;
	}

	public String getId() {
		return id;
	}

	public SimulationEndSettings getSimulationEndSettings() {
		return simulationEndSettings;
	}


	private Object readResolve() throws IOException {
		if (this.simulationEndSettings == null) {
			throw new IOException("Cannot read object: SimulationEnd is compulsory.");
		}
		return this;
	}
}
