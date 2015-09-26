package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SettingsLoader;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationRunSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationRunSettingsProfile;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariableSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariablesSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables.SimulationVariableSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables.SimulationVariableSettingFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class FetchVariableDefinitionsProcessor extends PackageTaskProcessorBase {

	private final SettingsLoader settingsLoader = new SettingsLoader();
	private final String simulationSettingsRelativePath;
	private final SimulationVariableSettingFactory simulationVariableSettingFactory;

	public FetchVariableDefinitionsProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler,
			String simulationSettingsRelativePath, SimulationVariableSettingFactory simulationVariableSettingFactory) {
		super(executor, exceptionHandler);

		this.simulationSettingsRelativePath = simulationSettingsRelativePath;
		this.simulationVariableSettingFactory = simulationVariableSettingFactory;
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {

		String preparingDirectory = task.getPreparingDirectory();

		File simulationSettingsFile = new File(preparingDirectory, simulationSettingsRelativePath);
		String runProfile = task.getRunProfile();

		try {
			// fetch variables
			VariablesSettings variablesSettings = loadVariablesSettings(simulationSettingsFile, runProfile);

			List<SimulationVariableSetting> simulationVariableSettings = new ArrayList<>();
			for (VariableSetting variableSettData : variablesSettings.getVariables()) {
				SimulationVariableSetting simulationVariableSetting = simulationVariableSettingFactory
						.create(variableSettData);
				simulationVariableSettings.add(simulationVariableSetting);
			}
			
			// save to repository
			// TODO
			task.getVariableDefinitions()
			
			// simulationVariableSettingFactory
			//
			// writer.println(String.format("simulation.run.profileId=%s",
			// task.getRunProfile()));
			// writer.println(String.format("simulation.statistics.profileId=%s",
			// task.getStatisticsProfile()));
			// writer.println(String.format("simulation.asserts.profileId=%s",
			// task.getAssertsProfile()));

		} catch (Exception e) {
			throw new PackagingException(
					String.format("Exception occured during fetching variable definitions from file: '%s'.",
							simulationSettingsFile.getAbsolutePath()),
					e);
		}

		return true;
	}

	private VariablesSettings loadVariablesSettings(File simulationSettingsFile, String runProfile) {
		SimulationSettings simulationSettings = settingsLoader.load(simulationSettingsFile.getAbsolutePath());
		SimulationRunSettings simulationRunSettings = simulationSettings.getSimulationRunSettings();

		SimulationRunSettingsProfile simulationRunSettingsProfile = simulationRunSettings.getProfiles().stream()
				.filter(x -> x.getId() == runProfile).findAny().get();
		VariablesSettings variablesSettings = simulationRunSettingsProfile.getVariablesSettings();
		return variablesSettings;
	}
}
