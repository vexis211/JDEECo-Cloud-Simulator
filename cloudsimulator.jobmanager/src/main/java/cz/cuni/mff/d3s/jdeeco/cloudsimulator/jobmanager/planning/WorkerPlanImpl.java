package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning;

import java.util.Iterator;
import java.util.LinkedList;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers.WorkerInstance;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers.WorkerStatistics;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.WorkerStatus;

public class WorkerPlanImpl implements WorkerPlan {

	private final WorkerPlanChangeListener changeListener;
	private final long id;
	private final WorkerInstance worker;
	private final LinkedList<WorkerPlanItem> items = new LinkedList<>();
	private final WorkerStatistics workerStatistics;

	public WorkerPlanImpl(long id, WorkerInstance worker, WorkerPlanChangeListener changeListener,
			WorkerStatistics workerStatistics) {
		this.id = id;
		this.worker = worker;
		this.changeListener = changeListener;
		this.workerStatistics = workerStatistics;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public WorkerInstance getWorker() {
		return worker;
	}

	@Override
	public WorkerPlanItem getCurrentItem() {
		return items.isEmpty() ? null : items.getFirst();
	}

	@Override
	public DateTime getPlanEndTime() {
		if (!items.isEmpty())
			return items.getLast().getEndTime();

		WorkerStatus workerStatus = worker.getStatus();
		if (workerStatus == WorkerStatus.Stopped) {
			return DateTime.now().plus(workerStatistics.getAverageStartTimeInMillis());
		} else if (workerStatus == WorkerStatus.Starting) {
			DateTime plannedStartedTime = worker.getLastStatusChange().plus(workerStatistics.getAverageStartTimeInMillis());
			return plannedStartedTime.isAfterNow() ? plannedStartedTime : DateTime.now();
		} else {
			return DateTime.now();
		}
	}

	@Override
	public void addNextItem(WorkerPlanItem item) {
		if (!items.isEmpty()) {
			if (isFirstBeforeSecond(items.getLast(), item)) {
				items.add(item);
			} else {
				throw new PlanningException("Incorrect plan. Added item was colliding with last item.");
			}
		} else {
			items.add(item);
		}
		changeListener.planChanged(this);
	}

	private boolean isFirstBeforeSecond(WorkerPlanItem first, WorkerPlanItem second) {
		return first.getEndTime().getMillis() <= second.getStartTime().getMillis();
	}

	@Override
	public void update(WorkerInstance worker) {

		WorkerPlanItem currentItem = getCurrentItem();
		items.clear();

		switch (worker.getStatus()) {
		case StartingSimulation:
		case RunningSimulation:
			items.add(currentItem);
			break;

		default:
			break;
		}
	}

	@Override
	public int hashCode() {
		int result = 31 * 1 + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkerPlanImpl other = (WorkerPlanImpl) obj;
		return id == other.id;
	}

	@Override
	public Iterator<WorkerPlanItem> iterator() {
		return items.iterator();
	}
}
