package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.vcs.VCSType;

public class PackageTaskImpl implements PackageTask {

	private List<PackageTask> identicalTasksForProcessingStep = new ArrayList<PackageTask>();
	
	@Override
	public List<PackageTask> getIdenticalTasksForProcessingStep() {
		return identicalTasksForProcessingStep;
	}

	@Override
	public VCSType getRepositoryType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRepositoryRemoteUrl() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getRelativePathToPomFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getMavenGoals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPreparingDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPackageLocalPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUploadName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getRepositoryLocalPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRepositoryLocalPath(String localPath) {
		// TODO Auto-generated method stub
		
	}	
}
