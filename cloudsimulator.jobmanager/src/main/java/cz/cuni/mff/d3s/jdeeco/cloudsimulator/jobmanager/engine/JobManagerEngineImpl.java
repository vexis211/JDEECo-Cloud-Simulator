package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.control.ProcessNewExecutionCommand;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatus;

public class JobManagerEngineImpl implements JobManagerEngine {
	
	@Resource
	private JobManagerUpdateQueue jobManagerUpdateQueue;

	@Override
	public void start() {

		while (true) {
			List<JobManagerUpdate> updates = getNewUpdates();
			processUpdates(updates);
		}
	}

	private List<JobManagerUpdate> getNewUpdates() {
		List<JobManagerUpdate> updates;
		try {
			updates = jobManagerUpdateQueue.takeAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
			updates = new ArrayList<JobManagerUpdate>();
		}
		return updates;
	}

	private void processUpdates(List<JobManagerUpdate> updates) {
		List<WorkerStatus> workerStatuses = new ArrayList<WorkerStatus>();
		List<ProcessNewExecutionCommand> newExecutionCommands = new ArrayList<ProcessNewExecutionCommand>();
		
		for (JobManagerUpdate update : updates) {
			if (update instanceof WorkerStatus) {
				
			}
		}
		
	}

}
