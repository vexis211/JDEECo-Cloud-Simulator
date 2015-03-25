package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers.WorkerInstance;

public class SimulationPlanImpl implements SimulationPlan, WorkerPlanChangeListener {

	private final HashMap<WorkerInstance, WorkerPlan> worker2plan = new HashMap<>();
	private final TreeSet<WorkerPlan> workerPlans;

	private final WorkerPlanFactory workerPlanFactory;

	public SimulationPlanImpl(WorkerPlanFactory workerPlanFactory) {
		this.workerPlanFactory = workerPlanFactory;

		// create priority queue
		Comparator<WorkerPlan> comparator = (p1, p2) -> {
			return p1.getPlanEndTime().compareTo(p2.getPlanEndTime());
		};
		this.workerPlans = new TreeSet<WorkerPlan>(comparator);
	}

	@Override
	public WorkerPlan getPlanForFirstEndingWorker() {
		return workerPlans.first();
	}

	@Override
	public WorkerPlan getPlanForLastEndingWorkerBefore(DateTime dateTime) {
		for (Iterator<WorkerPlan> iterator = workerPlans.descendingIterator(); iterator.hasNext();) {
			WorkerPlan workerPlan = iterator.next();
			if (workerPlan.getPlanEndTime().isBefore(dateTime)) {
				return workerPlan;
			}
		}
		return null;
	}

	@Override
	public boolean isAnythingPlanned() {
		for (WorkerPlan workerPlan : workerPlans) {
			if (workerPlan.getCurrentItem() != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<WorkerPlan> getWorkerPlans() {
		return new ArrayList<WorkerPlan>(workerPlans);
	}

	@Override
	public int getWorkerPlanCount() {
		return workerPlans.size();
	}

	@Override
	public WorkerPlan addWorkerPlan(WorkerInstance worker) {
		WorkerPlan workerPlan = workerPlanFactory.create(worker, this);
		workerPlans.add(workerPlan);
		worker2plan.put(worker, workerPlan);
		return workerPlan;
	}

	@Override
	public void removeWorkerPlan(WorkerPlan workerPlan) {
		worker2plan.remove(workerPlan.getWorker());
		workerPlans.remove(workerPlan);
	}

	@Override
	public void update(List<WorkerInstance> currentWorkers) {

		// clear plan min-heap
		workerPlans.clear();

		// update plans and add plans for new workers
		for (WorkerInstance worker : currentWorkers) {
			if (worker2plan.containsKey(worker)) {
				WorkerPlan workerPlan = worker2plan.get(worker);
				workerPlan.update(worker);
				workerPlans.add(workerPlan);
			} else {
				addWorkerPlan(worker);
			}
		}

		// remove worker-plan mapping for removed workers
		if (currentWorkers.size() != worker2plan.size()) {
			Set<WorkerInstance> currentWorkersSet = new HashSet<>(currentWorkers);
			List<WorkerInstance> toRemove = new ArrayList<>();
			for (WorkerInstance workerAsKey : worker2plan.keySet()) {
				if (!currentWorkersSet.contains(workerAsKey)) {
					toRemove.add(workerAsKey);
				}
			}
			toRemove.forEach(x -> worker2plan.remove(x));
		}
	}

	@Override
	public void planChanged(WorkerPlan workerPlan) {
		workerPlans.remove(workerPlan);
		workerPlans.add(workerPlan);
	}
}
