package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.File;
import java.io.InputStream;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.CloudDataService;

public class SimulationDataLoaderImpl implements SimulationDataLoader {

	private final CloudDataService cloudDataService;
	
	private final String dataContainerName;
	private final String resultsContainerName;
	private final String logsContainerName;

	
	public SimulationDataLoaderImpl(CloudDataService cloudDataService, String dataContainerName,
			String resultsContainerName, String logsContainerName) {
		this.cloudDataService = cloudDataService;
		this.dataContainerName = dataContainerName;
		this.resultsContainerName = resultsContainerName;
		this.logsContainerName = logsContainerName;
	}
	
	@Override
	public InputStream downloadPackage(int executionId) {
		return cloudDataService.download(dataContainerName, Integer.toString(executionId));
	}

	@Override
	public void uploadLogs(String sourcePath, int runId) {
		cloudDataService.upload(new File(sourcePath), logsContainerName, Integer.toString(runId));
	}

	@Override
	public void uploadResults(String sourcePath, int runId) {
		cloudDataService.upload(new File(sourcePath), resultsContainerName, Integer.toString(runId));
	}
}
