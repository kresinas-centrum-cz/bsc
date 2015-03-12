package net.kresina.bsc.javatest.test;

import static org.junit.Assert.*;

import java.io.File;

import net.kresina.bsc.javatest.components.Messages;
import net.kresina.bsc.javatest.service.Service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class BscTest {

	Service service;
	
	@Before
	public void setUpBefore() throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-config.xml" });
		this.service = (Service) context.getBean("service");
	}
	
	@Test
	public final void testGetKeeper() {
		assertNotNull(service.getKeeper());
	}

	@Test
	public final void testInitKeeper() {
		String testFileName = "src/test/resources/input";
		File inputFile = new File(testFileName);
		service.initKeeper(inputFile);
		assertNotNull("keeper test file input", service.getKeeper());
		assertEquals("print usd test", "USD 500,00\n", service.getKeeper().printCurrency("USD"));
		assertEquals("print czk test", "CZK 5005,00 (USD 196,49)\n", service.getKeeper().printCurrency("CZK"));
	}


	@Test
	public final void testInputValues(){
		assertNotNull("keeper test file input", service.getKeeper());
		service.readLineIn("CZK 3000");
		assertEquals("manual input test", "CZK 3000,00 (USD 117,77)\n", service.getKeeper().printCurrency("CZK"));
		assertEquals("manual input test error", "CZK3000", service.readLineIn("CZK3000"));  // might be choosen as not valid
		assertEquals("manual input test error", "CZK 3000", service.readLineIn("CZK 3000"));
	}

	@Test
	public final void testInputValuesError(){
		assertNotNull("keeper test file input", service.getKeeper());
		assertEquals("manual input test error", Messages.getString("Service.stringErrorInput"), service.readLineIn("CZ 3000"));
		assertEquals("manual input test error", Messages.getString("Service.stringErrorInput"), service.readLineIn("CZ 30ff00"));
		assertEquals("manual input test error", Messages.getString("Service.stringErrorInput"), service.readLineIn("CvZ 3000"));
		assertEquals("manual input test error", Messages.getString("Service.stringErrorInput"), service.readLineIn("500"));
		assertEquals("manual input test error", Messages.getString("Service.stringErrorInput"), service.readLineIn("CZKd3000"));
	}
}

