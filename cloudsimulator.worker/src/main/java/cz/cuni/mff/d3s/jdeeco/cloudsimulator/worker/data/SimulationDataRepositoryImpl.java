package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.annotation.Resource;

public class SimulationDataRepositoryImpl implements SimulationDataRepository {

	private final HashMap<String, String> simulationDataCache = new HashMap<>();

	private final String dataParentDirectory;

	@Resource
	private SimulationDataLoader simulationDataLoader;

	@Resource
	private SimulationDataArchiver simulationDataArchiver;

	
	public SimulationDataRepositoryImpl(String dataParentDirectory) {
		this.dataParentDirectory = dataParentDirectory;
	}

	@Override
	public String getData(String sourceUri) {
		if (simulationDataCache.containsKey(sourceUri))
			return simulationDataCache.get(sourceUri);

		InputStream archiveStream = simulationDataLoader.download(sourceUri);

		String dataPath = generateDataPath(sourceUri);
		simulationDataArchiver.extract(archiveStream, dataPath);

		simulationDataCache.put(sourceUri, dataPath);
		return dataPath;
	}

	private String generateDataPath(String sourceUri) {
		return dataParentDirectory + "//" + ""; // TODO generate GUID!!!
	}

	@Override
	public void saveResults(SimulationData data, String targetUri, String targetLogsUri) {
		// results
		OutputStream resultsArchiveStream = simulationDataArchiver.compress(data.getExecutionPath());
		simulationDataLoader.upload(resultsArchiveStream, targetUri);

		// logs
		simulationDataLoader.upload(data.getLogPath(), targetLogsUri);
	}

	@Override
	public void clear() {
		for (String directoryPath : simulationDataCache.values()) {
			try {
				Files.delete(Paths.get(directoryPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		simulationDataCache.clear();
	}
}
