package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers;

import java.util.UUID;

public class WorkerIdGeneratorImpl implements WorkerIdGenerator {

	@Override
	public String generate() {
		return ID_PREFIX + UUID.randomUUID().toString();
	}

}
