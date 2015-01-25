package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;

public class PackageManagerImpl implements PackageManager, PackagePreparatorListener {

	private final Object locker = new Object();
	private final HashMap<Integer, PackageManagerEntry> entries = new HashMap<>();

	private final PackageTaskFactory packageTaskFactory;
	private final PackagePreparator packagePreparator;

	public PackageManagerImpl(PackageTaskFactory packageTaskFactory, PackagePreparator packagePreparator) {
		this.packageTaskFactory = packageTaskFactory;
		this.packagePreparator = packagePreparator;
	}

	@Override
	public String getPackageLocation(SimulationExecution execution) {
		Integer executionId = execution.getId();

		synchronized (locker) {
			if (entries.containsKey(executionId)) {
				return entries.get(executionId).packageLocation;
			} else {
				return null;
			}
		}
	}

	@Override
	public void preparePackage(SimulationExecution execution, PackageManagerListener listener) {
		Integer executionId = execution.getId();

		synchronized (locker) {
			if (entries.containsKey(executionId)) {
				PackageManagerEntry entry = entries.get(executionId);
				if (entry.packageLocation == null) {
					entry.listeners.add(listener);
				} else {
					listener.packagePrepared(execution, entry.packageLocation);
				}
			} else {
				// create and add entry
				PackageManagerEntry entry = new PackageManagerEntry(execution);
				entry.listeners.add(listener);
				entries.put(executionId, entry);

				// prepare package
				PackageTask packageTask = packageTaskFactory.create(execution);
				packagePreparator.preparePackage(packageTask, this);
			}
		}
	}

	@Override
	public void packagePrepared(PackageTask packageTask) {
		int executionId = packageTask.getId();
		String packageLocation = packageTask.getUploadName();
		List<PackageManagerListener> listeners;
		SimulationExecution execution;

		synchronized (locker) {
			PackageManagerEntry entry = entries.get(executionId);
			listeners = new ArrayList<>(entry.listeners);
			execution = entry.execution;

			entry.packageLocation = packageLocation;
		}

		listeners.forEach(x -> x.packagePrepared(execution, packageLocation));
	}

	@Override
	public void packageExceptionOccured(PackageTask packageTask, PackagingException e) {
		// TODO Auto-generated method stub
		System.out.println(String.format("packageExceptionOccured: %s, PackagingException: %s", packageTask, e));
	}

	private class PackageManagerEntry {
		private final List<PackageManagerListener> listeners = new ArrayList<>();
		private final SimulationExecution execution;
		private String packageLocation;

		public PackageManagerEntry(SimulationExecution execution) {
			this.execution = execution;
		}
	}
}
