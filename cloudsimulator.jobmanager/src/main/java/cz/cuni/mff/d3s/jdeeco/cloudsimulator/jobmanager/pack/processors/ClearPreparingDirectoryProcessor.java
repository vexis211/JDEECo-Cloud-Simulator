package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class ClearPreparingDirectoryProcessor extends PackageTaskProcessorBase {

	public ClearPreparingDirectoryProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler) {
		super(executor, exceptionHandler);
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {

		String preparingDirectory = task.getPreparingDirectory();

		try {
			FileUtils.deleteDirectory(new File(preparingDirectory));
		} catch (IOException e) {
			throw new PackagingException(String.format(
					"Exception occured during removing preparing directory. Preparing directory: '%s'.",
					preparingDirectory), e);
		}

		return true;
	}
}