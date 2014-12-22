package cloudsimulator.worker;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cloudsimulator.worker.engine.WorkerEngine;

public class App {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

		WorkerEngine engine = (WorkerEngine) context.getBean("workerEngine");
		engine.start();
		
		context.close();
	}
}
