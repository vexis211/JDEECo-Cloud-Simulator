package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.processors;

import java.io.File;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.CloudDataService;

public class UploadPackageProcessor extends PackageTaskProcessorBase {

	private final String packageContainerName;
	private final CloudDataService cloudDataService;

	public UploadPackageProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler,
			String packageContainerName, CloudDataService cloudDataService) {
		super(executor, exceptionHandler);
		this.packageContainerName = packageContainerName;
		this.cloudDataService = cloudDataService;
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {

		String packageLocalPath = task.getPackageLocalPath();
		String uploadName = task.getUploadName();

		try {
			cloudDataService.upload(new File(packageLocalPath), packageContainerName, uploadName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PackagingException(
					String.format(
							"Exception occured during uploading package to cloud. Source: '%s', Target container name: '%s', Target name: '%s'.",
							packageLocalPath, packageContainerName, uploadName), e);
		}

		return true;
	}
}