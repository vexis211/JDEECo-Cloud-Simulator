package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationConfiguration;

public class SimulationConfigurationDaoImpl extends BaseDaoImpl<SimulationConfiguration> implements
		SimulationConfigurationDao {

	public SimulationConfigurationDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<SimulationConfiguration> findByProjectId(int projectId) {
		return findListByCriteria(Restrictions.eq("project.id", projectId));
	}
}
