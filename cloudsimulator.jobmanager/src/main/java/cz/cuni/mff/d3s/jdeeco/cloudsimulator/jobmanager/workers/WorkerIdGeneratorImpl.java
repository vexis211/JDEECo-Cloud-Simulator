package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers;

import java.util.UUID;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.workers.WorkersCommon;

public class WorkerIdGeneratorImpl implements WorkerIdGenerator {

	@Override
	public String generate() {
		return WorkersCommon.ID_PREFIX + UUID.randomUUID().toString();
	}

}
