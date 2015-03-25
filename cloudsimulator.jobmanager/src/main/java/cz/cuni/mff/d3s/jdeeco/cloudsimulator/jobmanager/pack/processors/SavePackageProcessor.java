package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors;

import java.io.File;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationDataStorageService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.CloudDataService;

public class SavePackageProcessor extends PackageTaskProcessorBase {

	private final String packageContainerName;
	private final SimulationDataStorageService simulationDataStorageService;

	public SavePackageProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler,
			String packageContainerName, SimulationDataStorageService simulationDataStorageService) {
		super(executor, exceptionHandler);
		this.packageContainerName = packageContainerName;
		this.simulationDataStorageService = simulationDataStorageService;
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {

		String packageLocalPath = task.getPackageLocalPath();
		String savePath = task.getSaveName();

		try {
			// TODO!!!!!!!!!!!!!!!!!!
//			void savePackage(String sourcePath, String target);
//			simulationDataStorageService.getDataPath(packageLocalPath)
//			simulationDataStorageService.upload(new File(packageLocalPath), packageContainerName, uploadName);
		} catch (Exception e) {
			e.printStackTrace();
//			throw new PackagingException(
//					String.format(
//							"Exception occured during uploading package to cloud. Source: '%s', Target container name: '%s', Target name: '%s'.",
//							packageLocalPath, packageContainerName, uploadName), e);
		}

		return true;
	}
}
