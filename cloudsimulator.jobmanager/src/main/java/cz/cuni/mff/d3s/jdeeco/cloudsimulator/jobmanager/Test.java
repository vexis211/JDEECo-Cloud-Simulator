package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager;

import java.io.File;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.PathEx;

public class Test {
	public static void main(String[] args) {
		String firstPart = "c:\\Users\\Jan\\SimulationData";
		String secondPart = "/executions/packages";
		
		String combine = PathEx.combine(firstPart, secondPart);
		File file = new File(combine);
		file.mkdirs();
	}
}
