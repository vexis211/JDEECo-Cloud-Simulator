package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

import java.util.HashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors.ClearPreparingDirectoryProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors.CodeRepositoryProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors.CompileCodeProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors.CopyCompiledToPreparingDirectoryProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors.NotifyPackageTaskCompletedProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors.PackageTaskProcessingListener;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors.PackageTaskProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors.UploadPackageProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors.ZipDataProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.vcs.CodeRepositoryManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.CloudDataService;

public class PackageCloudPreparatorImpl implements PackagePreparator, PackagingExceptionHandler,
		PackageTaskProcessingListener {

	private final HashMap<PackageTask, PackagePreparatorListener> taskRepo = new HashMap<>();
	private final PackageTaskProcessor firstProcessor;

	public PackageCloudPreparatorImpl(FutureExecutor executor, CodeRepositoryManager codeRepositoryManager,
			CloudDataService cloudDataService, String packageContainerName) {

		CodeRepositoryProcessor codeRepositoryProcessor = new CodeRepositoryProcessor(executor, this,
				codeRepositoryManager);
		CompileCodeProcessor compileCodeProcessor = new CompileCodeProcessor(executor, this);
		CopyCompiledToPreparingDirectoryProcessor copyCompiledToPreparingDirectoryProcessor = new CopyCompiledToPreparingDirectoryProcessor(
				executor, this);

		// TODO - run script processor
		ZipDataProcessor zipDataProcessor = new ZipDataProcessor(executor, this);
		UploadPackageProcessor uploadPackageProcessor = new UploadPackageProcessor(executor, this,
				packageContainerName, cloudDataService);
		ClearPreparingDirectoryProcessor clearPreparingDirectoryProcessor = new ClearPreparingDirectoryProcessor(
				executor, this);
		NotifyPackageTaskCompletedProcessor notifyPackageTaskCompletedProcessor = new NotifyPackageTaskCompletedProcessor(
				executor, this, this);

		codeRepositoryProcessor.continueProcess(compileCodeProcessor)
				.continueProcess(copyCompiledToPreparingDirectoryProcessor).continueProcess(zipDataProcessor)
				.continueProcess(uploadPackageProcessor).continueProcess(clearPreparingDirectoryProcessor)
				.continueProcess(notifyPackageTaskCompletedProcessor);

		this.firstProcessor = codeRepositoryProcessor;
	}

	@Override
	public void preparePackage(PackageTask packageTask, PackagePreparatorListener listener) {
		synchronized (taskRepo) {
			taskRepo.put(packageTask, listener);
		}
		this.firstProcessor.process(packageTask);
	}

	@Override
	public void exceptionOccured(PackageTask packageTask, PackagingException e) {
		PackagePreparatorListener listener;
		synchronized (taskRepo) {
			listener = taskRepo.remove(packageTask);
		}
		listener.packageExceptionOccured(packageTask, e);
	}

	@Override
	public void packageTaskCompleted(PackageTask packageTask) {
		PackagePreparatorListener listener;
		synchronized (taskRepo) {
			listener = taskRepo.remove(packageTask);
		}
		listener.packagePrepared(packageTask);
	}
}
