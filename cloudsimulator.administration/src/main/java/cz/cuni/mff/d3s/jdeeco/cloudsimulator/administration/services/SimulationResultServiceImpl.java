package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.SimulationExecutionDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.SimulationExecutionStatisticDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecutionStatistic;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRunStatistic;

public class SimulationResultServiceImpl implements SimulationResultService {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(SimulationResultServiceImpl.class);

	@Resource
	private SimulationExecutionDao simulationExecutionDao;

	@Resource
	private SimulationExecutionStatisticDao simulationExecutionStatisticDao;

	@Override
	public List<SimulationExecutionStatistic> listExecutionStatistics(int executionId) {
		SimulationExecution execution = simulationExecutionDao.findById(executionId);
		return new ArrayList<SimulationExecutionStatistic>(execution.getSimulationExecutionStatistics());
	}

	@Override
	public SimulationExecutionStatistic getExecutionStatisticById(int executionStatisticId) {
		return simulationExecutionStatisticDao.findById(executionStatisticId);
	}

	@Override
	public SimulationExecutionStatistic getExecutionStatistic(int executionId, String name) {
		return simulationExecutionStatisticDao.find(executionId, name);
	}

	@Override
	public List<SimulationRunStatistic> getAllRunStatistics(int executionStatisticId) {
		SimulationExecutionStatistic executionStatistic = getExecutionStatisticById(executionStatisticId);
		return new ArrayList<SimulationRunStatistic>(executionStatistic.getSimulationRunStatistics());
	}
}
