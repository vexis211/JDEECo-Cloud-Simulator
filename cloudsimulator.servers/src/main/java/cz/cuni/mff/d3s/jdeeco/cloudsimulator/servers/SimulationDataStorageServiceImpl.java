package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.PathEx;

public class SimulationDataStorageServiceImpl implements SimulationDataStorageService {

	private final String packageRootDirectory;
	private final String resultsRootDirectory;
	private final String logsRootDirectory;

	public SimulationDataStorageServiceImpl(String packageRootDirectory, String resultsRootDirectory,
			String logsRootDirectory) {
		this.packageRootDirectory = packageRootDirectory;
		this.resultsRootDirectory = resultsRootDirectory;
		this.logsRootDirectory = logsRootDirectory;
	}

	@Override
	public String getPackagePath(int executionId) {
		return PathEx.combine(packageRootDirectory, executionId);
	}

	@Override
	public void saveResults(String sourcePath, int runId) {
		try {
			FileUtils.copyDirectory(new File(sourcePath), new File(PathEx.combine(resultsRootDirectory, runId)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveLogs(String sourcePath, int runId) {
		try {
			FileUtils.copyDirectory(new File(sourcePath), new File(PathEx.combine(logsRootDirectory, runId)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
