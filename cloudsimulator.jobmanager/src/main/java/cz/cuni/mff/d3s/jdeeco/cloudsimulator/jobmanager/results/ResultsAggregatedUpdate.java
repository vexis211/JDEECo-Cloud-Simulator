package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;

public interface ResultsAggregatedUpdate extends JobManagerUpdate {

	int getExecutionId();
}
