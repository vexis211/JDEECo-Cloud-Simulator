package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;

public class SimulationExecutionDaoImpl extends BaseDaoImpl<SimulationExecution> implements SimulationExecutionDao {

	public SimulationExecutionDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	
	@Override
	public List<SimulationExecution> findAllNotCompleted() {		
		return listFullExecution(Restrictions.in("status", new SimulationStatus[] { SimulationStatus.Created,
				SimulationStatus.Started }));
	}

	@Override
	public List<SimulationExecution> findStoppedExecutionsWithIds(Collection<Integer> executionIds) {
		return listFullExecution(Restrictions.eq("status", SimulationStatus.Stopped),
				Restrictions.in("id", executionIds));
	}

	@SuppressWarnings("unchecked")
	private List<SimulationExecution> listFullExecution(Criterion... criterions) {
		Criteria crit = getSession().createCriteria(SimulationExecution.class)
				.createAlias("simulationConfiguration", "conf")
				.createAlias("conf.simulationData", "data")
				.setFetchMode("simulationRuns", FetchMode.JOIN)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		for (Criterion criterion : criterions) {
			crit.add(criterion);
		}
		
		return crit.list();
	}
}