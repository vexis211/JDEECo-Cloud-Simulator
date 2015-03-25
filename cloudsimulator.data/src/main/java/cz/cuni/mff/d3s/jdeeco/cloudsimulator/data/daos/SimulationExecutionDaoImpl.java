package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;

public class SimulationExecutionDaoImpl extends BaseDaoImpl<SimulationExecution> implements SimulationExecutionDao {

	@Override
	public List<SimulationExecution> findAllNotCompleted() {
		return findListByCriteria(Restrictions.in("status", new SimulationStatus[] { SimulationStatus.Created,
				SimulationStatus.Started }));
	}

	@Override
	public List<SimulationExecution> findStoppedExecutionsWithIds(Collection<Integer> executionIds) {
		return findListByCriteria(Restrictions.eq("status", SimulationStatus.Stopped),
				Restrictions.in("id", executionIds));
	}
}