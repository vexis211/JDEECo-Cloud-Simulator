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

	private final HashMap<Integer, String> simulationDataCache = new HashMap<>();

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
	public String getPackagePath(int executionId) {
		if (simulationDataCache.containsKey(executionId))
			return simulationDataCache.get(executionId);

		String dataPath = generateDataPath();

		try (InputStream archiveStream = simulationDataLoader.downloadPackage(executionId)) {
			simulationDataArchiver.extract(archiveStream, dataPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		simulationDataCache.put(executionId, dataPath);
		return dataPath;
	}

	private String generateDataPath() {
		return dataParentDirectory + "//" + UUID.randomUUID();
	}

	@Override
	public void saveResults(SimulationData data, int runId) {
		// results
		try {
			File tempZipFile = zipDataToSave(data.getExecutionPath(), "simulationResults");			
			simulationDataLoader.uploadResults(tempZipFile.getAbsolutePath(), runId);
			tempZipFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// logs
		try {
			File tempZipFile = zipDataToSave(data.getLogsPath(), "simulationLogs");			
			simulationDataLoader.uploadLogs(tempZipFile.getAbsolutePath(), runId);
			tempZipFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File zipDataToSave(String sourceDir, String zipPrefix) throws IOException {
		// create a temporary file.
		File tempZipFile = File.createTempFile(zipPrefix, ".zip");

		try (OutputStream outputStream = new FileOutputStream(tempZipFile)) {
			simulationDataArchiver.compress(sourceDir, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempZipFile;
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
