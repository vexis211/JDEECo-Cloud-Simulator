package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.VCSType;

public interface SimulationDataService {
	List<SimulationData> listDatas();
	List<SimulationData> listProjectDatas(int projectId);

	SimulationData getDataById(int dataId);

	void createData(int projectId, String name, String description, VCSType vcsType, String repositoryUrl, String pathToPom, String mavenGoals);
	void editData(int dataId, String name, String description, VCSType vcsType, String repositoryUrl, String pathToPom, String mavenGoals);
}