package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure.WorkerData;

public interface WorkerInfrastructureService {
	List<WorkerData> getWorkerDatas();
}
