
package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.JobManagerEngine;

public class App {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

		JobManagerEngine engine = (JobManagerEngine) context.getBean("jobManagerEngine");
		engine.start();
		
		context.close();
	}
}
