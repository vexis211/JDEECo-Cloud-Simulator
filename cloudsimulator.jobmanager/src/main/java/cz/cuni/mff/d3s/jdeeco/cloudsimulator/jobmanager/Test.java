package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager;

//import java.io.File;
//
//import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.PathEx;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.vcs.GitRepositoryProvider;

public class Test {
	public static void main(String[] args) {
//		testSlashesInPath();
		
		testUpdateRepo();
	}

	private static void testUpdateRepo() {
		//String remoteRepoPath = "https://github.com/vexis211/JDEECo-Cloud-Simulator-Test.git";
		String localRepoPath = "c:\\Users\\Jan\\simulationdrive\\packaging\\vcs\\Git\\peLHeM";
		
		GitRepositoryProvider repoProvider = new GitRepositoryProvider();
		repoProvider.update(localRepoPath);
	}

//	private static void testSlashesInPath() {
//		String firstPart = "c:\\Users\\Jan\\SimulationData";
//		String secondPart = "/executions/packages";
//		
//		String combine = PathEx.combine(firstPart, secondPart);
//		File file = new File(combine);
//		file.mkdirs();
//	}
}
