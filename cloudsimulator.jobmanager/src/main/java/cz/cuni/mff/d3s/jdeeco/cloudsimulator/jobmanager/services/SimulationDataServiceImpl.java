package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.ProjectDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos.SimulationDataDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.vcs.VCSType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security.UserHelper;

public class SimulationDataServiceImpl implements SimulationDataService {

	@Resource
	private SimulationDataDao simulationDataDao;

	@Resource
	private ProjectDao projectDao;

	@Override
	public List<SimulationData> listDatas() {
		return simulationDataDao.findAll();
	}

	@Override
	public List<SimulationData> listProjectDatas(int projectId) {
		return simulationDataDao.findByProjectId(projectId);
	}

	@Override
	public SimulationData getDataById(int dataId) {
		return simulationDataDao.findById(dataId);
	}

	@Transactional(readOnly = false)
	@Override
	public void createData(int projectId, String name, String description, VCSType vcsType, String repositoryUrl,
			String pathToPom, String mavenGoals) {

		User currentUser = UserHelper.getAuthenticatedUser();
		Project project = projectDao.findById(projectId);

		SimulationData data = new SimulationData(currentUser, project, name, description, vcsType, repositoryUrl,
				pathToPom, mavenGoals);
		
		simulationDataDao.saveOrUpdate(data);
	}

	@Transactional(readOnly = false)
	@Override
	public void editData(int dataId, String name, String description, VCSType vcsType, String repositoryUrl,
			String pathToPom, String mavenGoals) {
		SimulationData data = simulationDataDao.findById(dataId);

		data.setName(name);
		data.setDescription(description);
		data.setVcsType(vcsType);
		data.setRepositoryURL(repositoryUrl);
		data.setPathToPom(pathToPom);
		data.setMavenGoals(mavenGoals);

		simulationDataDao.saveOrUpdate(data);
	}
}
