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
	public String getPackagePath(String dataName) {
		return PathEx.combine(packageRootDirectory, dataName);
	}

	@Override
	public void saveResults(String sourcePath, String dataName) {
		try {
			FileUtils.copyDirectory(new File(sourcePath), new File(PathEx.combine(resultsRootDirectory, dataName)));
		} catch (IOException e) {
			e.printStackTrace();
			// TODO log
		}
	}

	@Override
	public void saveLogs(String sourcePath, String dataName) {
		try {
			FileUtils.copyFile(new File(sourcePath), new File(PathEx.combine(logsRootDirectory, dataName)));
		} catch (IOException e) {
			e.printStackTrace();
			// TODO log
		}
	}
}
