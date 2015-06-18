package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.PathEx;

public class SimulationDataStorageServiceImpl implements SimulationDataStorageService {

	private final Logger logger = LoggerFactory.getLogger(SimulationDataStorageServiceImpl.class);

	private final String remotePackageRootDirectory;
	private final String remoteResultsRootDirectory;
	private final String remoteLogsRootDirectory;

	public SimulationDataStorageServiceImpl(String remotePackageRootDirectory, String remoteResultsRootDirectory,
			String remoteLogsRootDirectory) {
		this.remotePackageRootDirectory = remotePackageRootDirectory;
		this.remoteResultsRootDirectory = remoteResultsRootDirectory;
		this.remoteLogsRootDirectory = remoteLogsRootDirectory;
	}

	@Override
	public String getPackagePath(int executionId) {
		return PathEx.combine(remotePackageRootDirectory, executionId);
	}

	@Override
	public void saveResults(String localSourcePath, SimulationId simulationId) {
		String remoteTargetPath = PathEx.combine(remoteResultsRootDirectory, simulationId.getRunId());
		try {
			FileUtils.copyDirectory(new File(localSourcePath), new File(remoteTargetPath));
		} catch (IOException e) {
			logger.error(String.format("Saving results failed. ID: '%s', Local path: '%s', Remote path: '%s'.",
					simulationId, localSourcePath, remoteTargetPath), e);
		}
	}

	@Override
	public void saveLogs(String localSourcePath, SimulationId simulationId) {
		String remoteTargetPath = PathEx.combine(remoteLogsRootDirectory, simulationId.getRunId());
		try {
			FileUtils.copyDirectory(new File(localSourcePath), new File(remoteTargetPath));
		} catch (IOException e) {
			logger.error(String.format("Saving logs failed. ID: '%s', Local path: '%s', Remote path: '%s'.",
					simulationId, localSourcePath, remoteTargetPath), e);
		}
	}
}
