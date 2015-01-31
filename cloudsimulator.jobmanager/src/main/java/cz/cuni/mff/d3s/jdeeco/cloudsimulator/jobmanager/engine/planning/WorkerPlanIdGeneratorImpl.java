package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning;

public class WorkerPlanIdGeneratorImpl implements WorkerPlanIdGenerator {

	private long prevId = 0;
	
	@Override
	public long generate() {
		return ++prevId;
	}

}
