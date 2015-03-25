package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class CopyCompiledToPreparingDirectoryProcessor extends PackageTaskProcessorBase {

	public CopyCompiledToPreparingDirectoryProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler) {
		super(executor, exceptionHandler);
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {

		String compileTargetDirectory = task.getCompileTargetDirectory();
		String preparingDirectory = task.getPreparingDirectory();

		try {
			FileUtils.copyDirectory(new File(compileTargetDirectory), new File(preparingDirectory));
		} catch (IOException e) {
			e.printStackTrace();
			throw new PackagingException(String.format(
					"Exception occured during copying compile target directory to preparing directory."
							+ " Compile target directory: '%s', Preparing directory: '%s'.", compileTargetDirectory,
					preparingDirectory), e);
		}

		return true;
	}
}