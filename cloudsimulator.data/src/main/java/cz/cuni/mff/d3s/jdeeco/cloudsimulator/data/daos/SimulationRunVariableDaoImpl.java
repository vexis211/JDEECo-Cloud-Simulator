package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import org.hibernate.SessionFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRunVariable;

public class SimulationRunVariableDaoImpl extends BaseDaoImpl<SimulationRunVariable>
		implements SimulationRunVariableDao {

	public SimulationRunVariableDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
