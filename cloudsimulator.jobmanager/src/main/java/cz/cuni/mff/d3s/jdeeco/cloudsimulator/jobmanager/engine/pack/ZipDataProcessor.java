package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.ZipHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class ZipDataProcessor extends PackageTaskProcessorBase {

	protected final HashMap<String, PackageTask> unfinishedTasks = new HashMap<>();

	public ZipDataProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler) {
		super(executor, exceptionHandler);
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {

		String source = task.getPreparingDirectory();
		String target = task.getPackageLocalPath();

		try {
			ZipHelper.zipRecursively(new File(source), new File(target));
		} catch (IOException e) {
			e.printStackTrace();
			throw new PackagingException(String.format(
					"Exception occured during zipping data directory. Source directory: '%s', Target file: '%s'.",
					source, target), e);
		}

		return true;
	}
}
