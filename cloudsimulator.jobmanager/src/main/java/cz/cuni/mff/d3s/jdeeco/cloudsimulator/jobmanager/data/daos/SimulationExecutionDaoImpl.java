package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;

public class SimulationExecutionDaoImpl extends BaseDaoImpl<SimulationExecution> implements SimulationExecutionDao {

	@Override
	public List<SimulationExecution> findAllNotCompleted() {
		return findListByCriteria(Restrictions.in("status", new SimulationStatus[] { SimulationStatus.Created,
				SimulationStatus.Started }));
	}

}