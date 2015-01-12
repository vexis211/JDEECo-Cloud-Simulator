package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.vcs;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.PathEx;

public class CodeRepositoryManagerImpl implements CodeRepositoryManager {

	private final HashMap<VCSType, VCSTypeEntry> vcsTypeEntries;
	private final String localRootDir;

	public CodeRepositoryManagerImpl(String localRootDir, List<RepositoryProvider> repositoryProviders) {
		this.localRootDir = localRootDir;
		this.vcsTypeEntries = new HashMap<>();

		for (RepositoryProvider repositoryProvider : repositoryProviders) {
			if (vcsTypeEntries.containsKey(repositoryProvider.getVCSType())) {
				throw new RuntimeException("More than one repository providers for VCS system: "
						+ repositoryProvider.getVCSType());
			}

			vcsTypeEntries.put(repositoryProvider.getVCSType(), new VCSTypeEntry(repositoryProvider));
		}
	}

	@Override
	public String prepareRepository(String remoteUrl, VCSType vcsType) {
		VCSTypeEntry entry = vcsTypeEntries.get(vcsType);
		return entry.getOrUpdateLocalPath(remoteUrl);
	}

	class VCSTypeEntry {
		private static final String REPODATA_FILENAME = "repodata.dat";
		
		private final RepositoryProvider provider;
		private final File rootDir;
		private final RepositoryPersistingMap repoMapping;

		public VCSTypeEntry(RepositoryProvider provider) {
			this.provider = provider;
			this.rootDir = new File(localRootDir, provider.getVCSType().name());
			this.repoMapping = new RepositoryPersistingMap(PathEx.combine(rootDir.getAbsolutePath(), REPODATA_FILENAME));
			this.rootDir.mkdirs();
		}

		public String getOrUpdateLocalPath(String remoteUrl) {
			String localPath = null;
			boolean wasContained = false;
			
			synchronized (repoMapping) {
				if (repoMapping.containsRemoteUrl(remoteUrl)) {
					localPath = repoMapping.getLocalPath(remoteUrl);
					wasContained = true;
				} else {
					localPath = getNewDir();
					repoMapping.put(remoteUrl, localPath);
				}
			}
			
			if (wasContained) provider.update(localPath);
			else provider.get(remoteUrl, localPath);
			
			return localPath;
		}

		// called only from getOrUpdateLocalPath - otherwise lock
		private String getNewDir() {
			String newDir;
			do {
				newDir = PathEx.combine(rootDir.getAbsolutePath(), RandomStringUtils.randomAlphabetic(6));
			} while (repoMapping.containsLocalPath(newDir));

			return newDir;
		}
	}
}
