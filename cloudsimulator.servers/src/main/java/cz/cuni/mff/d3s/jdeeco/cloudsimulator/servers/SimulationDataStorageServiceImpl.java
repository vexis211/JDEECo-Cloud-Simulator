package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.PathEx;

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
	public String getDataPath(String dataName) {
		return PathEx.combine(packageRootDirectory, dataName);
	}

	@Override
	public void saveResults(String source, String target) {
		try {
			FileUtils.copyDirectory(new File(source), new File(PathEx.combine(resultsRootDirectory, target)));
		} catch (IOException e) {
			e.printStackTrace();
			// TODO log
		}
	}

	@Override
	public void saveLogs(String source, String target) {
		try {
			FileUtils.copyFile(new File(source), new File(PathEx.combine(logsRootDirectory, target)));
		} catch (IOException e) {
			e.printStackTrace();
			// TODO log
		}
	}
}
