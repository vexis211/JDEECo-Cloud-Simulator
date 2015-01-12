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
	public InputStream download(String source) {
		return cloudDataService.download(dataContainerName, source);
	}

	@Override
	public void uploadLogs(String sourcePath, String target) {
		cloudDataService.upload(new File(sourcePath), logsContainerName, target);
	}

	@Override
	public void uploadResults(String sourcePath, String target) {
		cloudDataService.upload(new File(sourcePath), resultsContainerName, target);
	}
}
