package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import org.hibernate.SessionFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRunStatistic;

public class SimulationRunStatisticDaoImpl extends BaseDaoImpl<SimulationRunStatistic>
		implements SimulationRunStatisticDao {

	public SimulationRunStatisticDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
