package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup;

import java.util.Optional;

import org.apache.log4j.Logger;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.SimulationEndSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.SimulationRunSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.SimulationRunSettingsProfile;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.SimulationRunSettingsProvider;

public class SimulationRunConfigurationImpl implements SimulationRunConfiguration {

	private static Logger logger = Logger.getLogger(SimulationRunConfigurationImpl.class);

	private final String simulationRunProfileId;
	private final SimulationRunSettings settings;
	private SimulationEndSettings endSettings;

	public SimulationRunConfigurationImpl(SimulationRunSettingsProvider simulationRunSettingsProvider,
			String simulationRunProfileId) {
		this.settings = simulationRunSettingsProvider.getSimulationRunSettings();
		this.simulationRunProfileId = simulationRunProfileId;

		this.initializeSettings();
	}

	private final void initializeSettings() {
		Optional<SimulationRunSettingsProfile> profileOptional = this.settings.getProfiles().stream()
				.filter(x -> x.getId().equals(this.simulationRunProfileId)).findAny();

		if (profileOptional.isPresent()) {
			initializeSettingsProfile(profileOptional.get());
		} else {
			String message = String.format("Run profile '%s' is not defined.", this.simulationRunProfileId);
			logger.error(message);
			throw new RuntimeException(message);
		}
	}

	private final void initializeSettingsProfile(SimulationRunSettingsProfile selectedProfile) {
		this.endSettings = selectedProfile.getSimulationEndSettings();
	}

	@Override
	public String getProfileId() {
		return this.simulationRunProfileId;
	}

	@Override
	public SimulationEndSettings getEndSettings() {
		return this.endSettings;
	}

}
