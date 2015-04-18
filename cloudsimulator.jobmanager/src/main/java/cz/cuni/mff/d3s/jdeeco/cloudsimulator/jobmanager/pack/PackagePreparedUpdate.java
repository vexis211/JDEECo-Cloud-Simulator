package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;

public interface PackagePreparedUpdate extends JobManagerUpdate {

	int getExecutionId();
}
