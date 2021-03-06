package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.vcs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryPersistingMap {

	private final Logger logger = LoggerFactory.getLogger(RepositoryPersistingMap.class);

	private static final String SEPARATOR = "\t";

	private final HashMap<String, String> remote2Local = new HashMap<>();
	private final HashMap<String, String> local2Remote = new HashMap<>();
	private final File dataFile;

	public RepositoryPersistingMap(String filePath) {
		this.dataFile = new File(filePath);
		Load();
	}

	private void Load() {
		if (dataFile.exists()) {
			try (FileReader fileReader = new FileReader(dataFile);
					BufferedReader reader = new BufferedReader(fileReader)) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split(SEPARATOR);
					if (parts.length == 2) {
						remote2Local.put(parts[0], parts[1]);
						local2Remote.put(parts[1], parts[0]);
					} else {
						logger.error("Incorrect repository file format! File: {}. Line: {}",
								dataFile.getAbsolutePath(), line);
					}
				}

			} catch (IOException e) {
				logger.error("Error loading repository persisting map.", e);
			}

			Save();
		}
	}

	private void Save() {
		try (FileWriter fileWriter = new FileWriter(dataFile); BufferedWriter writer = new BufferedWriter(fileWriter)) {
			for (Map.Entry<String, String> entry : remote2Local.entrySet()) {
				writer.append(entry.getKey()).append(SEPARATOR).append(entry.getValue());
			}
		} catch (IOException e) {
			logger.error("Error saving repository persisting map.", e);
		}
	}

	public boolean containsRemoteUrl(String remoteUrl) {
		return remote2Local.containsKey(remoteUrl);
	}

	public boolean containsLocalPath(String localPath) {
		return local2Remote.containsKey(localPath);
	}

	public String getLocalPath(String remoteUrl) {
		return remote2Local.get(remoteUrl);
	}

	public void put(String remoteUrl, String localPath) {
		try (FileWriter fileWriter = new FileWriter(dataFile, true);
				BufferedWriter writer = new BufferedWriter(fileWriter)) {
			writer.append(remoteUrl).append(SEPARATOR).append(localPath);
		} catch (IOException e) {
			logger.error(String.format(
					"Error occured while putting item to repository persisting map. Remote repo: %s. Local repo: %s.",
					remoteUrl, localPath), e);
		}

		remote2Local.put(remoteUrl, localPath);
		local2Remote.put(localPath, remoteUrl);
	}
}
