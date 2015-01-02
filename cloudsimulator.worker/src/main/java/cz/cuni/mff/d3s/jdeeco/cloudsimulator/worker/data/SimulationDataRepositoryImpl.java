package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class SimulationDataRepositoryImpl implements SimulationDataRepository {

	private final HashMap<String, String> simulationDataCache = new HashMap<>();

	private final String dataParentDirectory;

	private final SimulationDataLoader simulationDataLoader;
	private final SimulationDataArchiver simulationDataArchiver;

	public SimulationDataRepositoryImpl(String dataParentDirectory, SimulationDataLoader simulationDataLoader,
			SimulationDataArchiver simulationDataArchiver) {
		this.dataParentDirectory = dataParentDirectory;
		this.simulationDataLoader = simulationDataLoader;
		this.simulationDataArchiver = simulationDataArchiver;
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
