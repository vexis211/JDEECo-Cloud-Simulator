package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class SetProfilesProcessor extends PackageTaskProcessorBase {

	public SetProfilesProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler) {
		super(executor, exceptionHandler);
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {

		String preparingDirectory = task.getPreparingDirectory();

		// create property files with profile names/IDs
		File profilesPropFile = new File(preparingDirectory, "simulation-profiles.properties");

		try (PrintWriter writer = new PrintWriter(profilesPropFile)) {
			writer.println(String.format("simulation.run.profileId=%s", task.getRunProfile()));
			writer.println(String.format("simulation.statistics.profileId=%s", task.getStatisticsProfile()));
			writer.println(String.format("simulation.asserts.profileId=%s", task.getAssertsProfile()));

		} catch (IOException e) {
			throw new PackagingException(String.format(
					"Exception occured during creating profile properties file: '%s', Package directory: '%s'.",
					preparingDirectory), e);
		}

		return true;
	}
}
