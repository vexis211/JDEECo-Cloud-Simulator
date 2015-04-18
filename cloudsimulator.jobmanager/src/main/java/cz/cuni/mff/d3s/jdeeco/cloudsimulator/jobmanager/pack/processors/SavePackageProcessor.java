package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationDataStorageService;

public class SavePackageProcessor extends PackageTaskProcessorBase {

	private final SimulationDataStorageService simulationDataStorageService;

	public SavePackageProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler,
			SimulationDataStorageService simulationDataStorageService) {
		super(executor, exceptionHandler);
		this.simulationDataStorageService = simulationDataStorageService;
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {

		String preparingDirectory = task.getPreparingDirectory();
		String packageDirectory = simulationDataStorageService.getPackagePath(task.getExecutionId());

		try {
			FileUtils.copyDirectory(new File(preparingDirectory), new File(packageDirectory));
		} catch (IOException e) {
			throw new PackagingException(String.format(
					"Exception occured during copying data from preparing directory to package directory."
							+ " Preparing directory: '%s', Package directory: '%s'.", preparingDirectory,
					packageDirectory), e);
		}

		return true;
	}
}
