package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

public class CloudSimulationDataRepositoryImpl implements SimulationDataRepository {

	private final HashMap<String, String> simulationDataCache = new HashMap<>();

	private final String dataParentDirectory;

	private final SimulationDataLoader simulationDataLoader;
	private final SimulationDataArchiver simulationDataArchiver;

	public CloudSimulationDataRepositoryImpl(String dataParentDirectory, SimulationDataLoader simulationDataLoader,
			SimulationDataArchiver simulationDataArchiver) {
		this.dataParentDirectory = dataParentDirectory;
		this.simulationDataLoader = simulationDataLoader;
		this.simulationDataArchiver = simulationDataArchiver;
	}

	@Override
	public String getData(String dataName) {
		if (simulationDataCache.containsKey(dataName))
			return simulationDataCache.get(dataName);

		String dataPath = generateDataPath();

		try (InputStream archiveStream = simulationDataLoader.download(dataName)) {
			simulationDataArchiver.extract(archiveStream, dataPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		simulationDataCache.put(dataName, dataPath);
		return dataPath;
	}

	private String generateDataPath() {
		return dataParentDirectory + "//" + UUID.randomUUID();
	}

	@Override
	public void saveResults(SimulationData data, String dataName) {
		// results
		try {
			saveResultsInternal(data, dataName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// logs
		simulationDataLoader.uploadLogs(data.getLogPath(), dataName);
	}

	private void saveResultsInternal(SimulationData data, String dataName) throws IOException {
		// create a temporary file.
		File tempZipFile = File.createTempFile("simulationResults", ".zip");

		try (OutputStream outputStream = new FileOutputStream(tempZipFile)) {
			simulationDataArchiver.compress(data.getExecutionPath(), outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		simulationDataLoader.uploadResults(tempZipFile.getAbsolutePath(), dataName);
		
		// delete temporary file when you finish to use it.
		// if streams where not correctly closed this might fail (return false)
		tempZipFile.delete();
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
