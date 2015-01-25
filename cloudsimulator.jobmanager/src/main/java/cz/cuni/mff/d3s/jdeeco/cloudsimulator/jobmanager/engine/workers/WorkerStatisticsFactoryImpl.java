package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers;

public class WorkerStatisticsFactoryImpl implements WorkerStatisticsFactory {

	@Override
	public WorkerStatistics create() {
		return new WorkerStatisticsImpl();
	}

}
