package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SettingsLoader;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationRunSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationRunSettingsProfile;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariablesSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class FetchVariableDefinitionsProcessor extends PackageTaskProcessorBase {

	private final SettingsLoader settingsLoader = new SettingsLoader();
	private final String simulationSettingsRelativePath;

	public FetchVariableDefinitionsProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler,
			String simulationSettingsRelativePath) {
		super(executor, exceptionHandler);

		this.simulationSettingsRelativePath = simulationSettingsRelativePath;
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {

		String preparingDirectory = task.getPreparingDirectory();

		File simulationSettingsFile = new File(preparingDirectory, simulationSettingsRelativePath);

		try {
			SimulationSettings simulationSettings = settingsLoader.load(simulationSettingsFile.getAbsolutePath());
			String runProfile = task.getRunProfile();
			SimulationRunSettings simulationRunSettings = simulationSettings.getSimulationRunSettings();

			SimulationRunSettingsProfile simulationRunSettingsProfile = simulationRunSettings.getProfiles().stream()
					.filter(x -> x.getId() == runProfile).findAny().get();
			VariablesSettings variablesSettings = simulationRunSettingsProfile.getVariablesSettings();

			writer.println(String.format("simulation.run.profileId=%s", task.getRunProfile()));
			writer.println(String.format("simulation.statistics.profileId=%s", task.getStatisticsProfile()));
			writer.println(String.format("simulation.asserts.profileId=%s", task.getAssertsProfile()));

		} catch (IOException e) {
			throw new PackagingException(
					String.format("Exception occured during fetching variable definitions from file: '%s'.",
							simulationSettingsFile.getAbsolutePath()),
					e);
		}

		return true;
	}
}
