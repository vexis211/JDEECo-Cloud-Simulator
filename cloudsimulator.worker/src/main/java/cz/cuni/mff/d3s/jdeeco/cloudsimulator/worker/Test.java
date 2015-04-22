package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker;

import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
		WorkerInfoProvider workerInfoProvider = new WorkerInfoProviderImpl();
		System.out.println(workerInfoProvider.getWorkerId());
		System.in.read();
	}
}
