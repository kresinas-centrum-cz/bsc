package net.kresina.bsc.javatest.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.kresina.bsc.javatest.beans.Payment;
import net.kresina.bsc.javatest.components.Keeper;
import net.kresina.bsc.javatest.components.Messages;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public class Service extends Thread {

	Logger log = Logger.getLogger(this.getClass());

	Keeper keeper;

	Scheduler scheduler;
	
	public void run() {
		try {
			scheduler.start();
		} catch (SchedulerException e1) {
			log.error(e1);
			System.exit(-5);
		}
		System.out.println(Messages.getString("Service.string0"));
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);

		String cr = "";
		while (!(cr.equals(Messages.getString("Service.string2")))) { 
			try {
				cr = in.readLine();
			} catch (IOException e) {
				cr = Messages.getString("Service.string2"); 
				e.printStackTrace();
			}
			if (!(cr.equals(Messages.getString("Service.string2")))) { 
				System.out.println(Messages.getString("Service.string5") + cr); 
				log.info(Messages.getString("Service.string5") + cr); 
				cr = readLineIn(cr);
			}
		}
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			log.error(e);
			System.exit(-3);
		}
		System.out.println(Messages.getString("Service.string7")); 
		return;
		}

	public String readLineIn(String cr) {
		cr = cr.trim();

		try {
			String code = cr.substring(0, 3).trim();
			String amount = cr.substring(3).trim();
			keeper.addRecord(new Payment(code, amount));
		} catch (Exception e) {
			log.error(e);
			System.out.println(Messages.getString("Service.stringErrorInput")); 
			cr = Messages.getString("Service.stringErrorInput");
		}
		return cr;
	}

	public Keeper getKeeper() {
		return keeper;
	}

	public void setKeeper(Keeper keeper) {
		this.keeper = keeper;
	}
	
	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	
	public void initKeeper(File inputFile) {
		String line;
		InputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(inputFile);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				readLineIn(line);
			}
		} catch (FileNotFoundException fe) {
			log.error(fe);
		} catch (IOException e) {
			log.error(e);
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
			} catch (IOException ioe) {
				log.error(ioe);
			}
		}
	}
	
}
