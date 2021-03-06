package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.ProjectDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.daos.SimulationDataDao;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.VCSType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.UserHelper;

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
			String pomDirectory, String mavenGoals, String startupFile) {

		User currentUser = UserHelper.getAuthenticatedUser();
		Project project = projectDao.findById(projectId);

		SimulationData data = new SimulationData(currentUser, project, name, description, vcsType, repositoryUrl,
				pomDirectory, mavenGoals, startupFile);
		
		simulationDataDao.saveOrUpdate(data);
	}

	@Transactional(readOnly = false)
	@Override
	public void editData(int dataId, String name, String description, VCSType vcsType, String repositoryUrl,
			String pomDirectory, String mavenGoals, String startupFile) {
		SimulationData data = simulationDataDao.findById(dataId);

		data.setName(name);
		data.setDescription(description);
		data.setVcsType(vcsType);
		data.setRepositoryURL(repositoryUrl);
		data.setPomDirectory(pomDirectory);
		data.setMavenGoals(mavenGoals);
		data.setStartupFile(startupFile);

		simulationDataDao.saveOrUpdate(data);
	}
}
