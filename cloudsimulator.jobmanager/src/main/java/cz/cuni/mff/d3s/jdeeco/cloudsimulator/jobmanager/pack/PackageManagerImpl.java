package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.variables.SimulationExecutionVariableDefinitions;

public class PackageManagerImpl implements PackageManager, PackagePreparatorListener {

	private final Logger logger = LoggerFactory.getLogger(PackageManagerImpl.class);

	private final Object locker = new Object();
	private final HashMap<Integer, PackageManagerEntry> entries = new HashMap<>();

	private final PackageTaskFactory packageTaskFactory;
	private final PackagePreparator packagePreparator;

	public PackageManagerImpl(PackageTaskFactory packageTaskFactory, PackagePreparator packagePreparator) {
		this.packageTaskFactory = packageTaskFactory;
		this.packagePreparator = packagePreparator;
	}

	@Override
	public boolean isPackagePrepared(SimulationExecution execution) {
		Integer executionId = execution.getId();

		synchronized (locker) {
			return entries.containsKey(executionId);
		}
	}

	@Override
	public void preparePackage(SimulationExecution execution, PackageManagerListener listener) {
		Integer executionId = execution.getId();
		SimulationExecutionVariableDefinitions variablesDefinition = null;

		boolean notifyPrepared = false;

		synchronized (locker) {
			if (entries.containsKey(executionId)) {
				PackageManagerEntry entry = entries.get(executionId);
				if (entry.isPrepared) {
					variablesDefinition = entry.variablesDefinition;
					notifyPrepared = true;
				} else {
					entry.listeners.add(listener);
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

		if (notifyPrepared) {
			listener.packagePrepared(execution.getId(), variablesDefinition);
		}
	}

	@Override
	public void packagePrepared(PackageTask packageTask) {
		int executionId = packageTask.getExecutionId();
		List<PackageManagerListener> listeners;
		SimulationExecution execution;

		synchronized (locker) {
			PackageManagerEntry entry = entries.get(executionId);
			listeners = new ArrayList<>(entry.listeners);
			entry.listeners.clear();
			execution = entry.execution;
			
			entry.variablesDefinition = packageTask.getVariableDefinitions();
			entry.isPrepared = true;
		}

		logger.info("Package for execution id: {} is prepared.", executionId);
		listeners.forEach(x -> x.packagePrepared(execution.getId(), packageTask.getVariableDefinitions()));
	}

	@Override
	public void packageExceptionOccured(PackageTask packageTask, PackagingException e) {
		logger.error("Error occured while performing packaging task: " + packageTask, e);
	}

	private class PackageManagerEntry {
		private final List<PackageManagerListener> listeners = new ArrayList<>();
		private final SimulationExecution execution;
		private boolean isPrepared = false;
		private SimulationExecutionVariableDefinitions variablesDefinition;

		public PackageManagerEntry(SimulationExecution execution) {
			this.execution = execution;
		}
	}
}
