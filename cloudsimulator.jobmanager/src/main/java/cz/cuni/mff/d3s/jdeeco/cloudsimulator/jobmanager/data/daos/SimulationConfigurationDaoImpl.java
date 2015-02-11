package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;

public class SimulationConfigurationDaoImpl extends BaseDaoImpl<SimulationConfiguration> implements
		SimulationConfigurationDao {

	@Override
	public List<SimulationConfiguration> findByProjectId(int projectId) {
		return findListByCriteria(Restrictions.eq("project.id", projectId));
	}
}
