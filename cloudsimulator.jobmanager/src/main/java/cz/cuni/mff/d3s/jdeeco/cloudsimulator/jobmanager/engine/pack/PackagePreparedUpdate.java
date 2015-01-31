package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;

public interface PackagePreparedUpdate extends JobManagerUpdate {

	int getExecutionId();

	String getPackageName();
}
