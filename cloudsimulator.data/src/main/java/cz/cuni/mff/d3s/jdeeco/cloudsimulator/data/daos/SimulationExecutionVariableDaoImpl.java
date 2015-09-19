package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecutionVariable;

public class SimulationExecutionVariableDaoImpl extends BaseDaoImpl<SimulationExecutionVariable>
		implements SimulationExecutionVariableDao {

	public SimulationExecutionVariableDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public SimulationExecutionVariable find(int executionId, String name) {
		return findUniqueByCriteria(Restrictions.eq("executionId", executionId), Restrictions.eq("name", name));
	}
}