package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import org.hibernate.SessionFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRunStatisticAggdata;

public class SimulationRunStatisticAggdataDaoImpl extends BaseDaoImpl<SimulationRunStatisticAggdata> implements SimulationRunStatisticAggdataDao {

	public SimulationRunStatisticAggdataDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
