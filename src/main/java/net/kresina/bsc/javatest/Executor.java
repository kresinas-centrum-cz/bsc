package net.kresina.bsc.javatest;

import java.io.File;

import net.kresina.bsc.javatest.service.Service;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Executor {

	public static void main(String[] args) {

		Logger log = Logger.getLogger(Executor.class);
		File inputFile = null;
		
		try {
			if (args.length > 0) {
				inputFile = new File(args[0]);
			}

			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-config.xml" });

			log.debug("getting service");
			Service service = (Service) context.getBean("service");
			if (inputFile != null && inputFile.exists()) {
				log.debug("initiating keeper");
				service.initKeeper(inputFile);
			}
			log.debug("getting scheduler");
			Scheduler scheduler = (Scheduler) context.getBean("scheduler");

			service.setScheduler(scheduler);
			
			Thread t1 = new Thread(service);
			log.info("starting input thread");
			t1.start();
		} catch (Exception e){
			log.error(e);
			System.exit(-8);
		}
	}
}
