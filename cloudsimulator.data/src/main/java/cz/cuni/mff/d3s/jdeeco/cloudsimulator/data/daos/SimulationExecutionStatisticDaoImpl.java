package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecutionStatistic;

public class SimulationExecutionStatisticDaoImpl extends BaseDaoImpl<SimulationExecutionStatistic>
		implements SimulationExecutionStatisticDao {

	public SimulationExecutionStatisticDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public SimulationExecutionStatistic find(int executionId, String name) {
		return findUniqueByCriteria(Restrictions.eq("executionId", executionId), Restrictions.eq("name", name));
	}
}