package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationData;

public interface SimulationDataDao extends BaseDao<SimulationData> {

	List<SimulationData> findByProjectId(int projectId);
}