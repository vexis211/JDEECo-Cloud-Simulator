package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import org.hibernate.SessionFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRun;

public class SimulationRunDaoImpl extends BaseDaoImpl<SimulationRun> implements SimulationRunDao {

	public SimulationRunDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
