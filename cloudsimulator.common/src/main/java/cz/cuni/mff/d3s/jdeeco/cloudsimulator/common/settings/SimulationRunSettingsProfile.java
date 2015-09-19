package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import java.io.IOException;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationEndSettings;

@XStreamAlias("SimulationRunProfile")
public class SimulationRunSettingsProfile {

	@XStreamAsAttribute
	@XStreamAlias("Id")
	private final String id;

	@XStreamAlias("SimulationEnd")
	private final SimulationEndSettings simulationEndSettings;

	@XStreamAlias("SimulationVariables")
	private final VariablesSettings variablesSettings;
	
	public SimulationRunSettingsProfile(String id, SimulationEndSettings simulationEndSettings, VariablesSettings variablesSettings) {
		this.id = id;
		this.simulationEndSettings = simulationEndSettings;
		this.variablesSettings = variablesSettings;
	}

	public String getId() {
		return id;
	}

	public SimulationEndSettings getSimulationEndSettings() {
		return simulationEndSettings;
	}

	public VariablesSettings getVariablesSettings() {
		return variablesSettings;
	}


	private Object readResolve() throws IOException {
		if (this.simulationEndSettings == null) {
			throw new IOException("Cannot read object: SimulationEnd is compulsory.");
		}
		return this;
	}
}
