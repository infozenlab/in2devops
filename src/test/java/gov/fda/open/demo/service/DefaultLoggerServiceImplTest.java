/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.fda.open.demo.config.LoggingRule;
import gov.fda.open.demo.model.enums.LogLevel;
import gov.fda.open.demo.service.loggable.Loggable;

import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * JUnit test class for {@link DefaultLoggerServiceImpl}
 */
public class DefaultLoggerServiceImplTest {

	/** The logger service. */
	private LoggerService loggerService;

	/** The logging rule */
	@Rule
    public TestRule turnOffLogging = new LoggingRule(DefaultLoggerServiceImpl.class);
 
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		loggerService = new DefaultLoggerServiceImpl();
	}

	/**
	 * Test is log level.
	 */
	@Test
	public void testIsLogLevel() {
		assertTrue(loggerService.isLogLevel(LogLevel.DEBUG, DefaultLoggerServiceImpl.class));
		assertFalse(loggerService.isLogLevel(LogLevel.DEBUG, Date.class));
	}

	/**
	 * Test log.
	 */
	@Test
	public void testLog() {
		loggerService.log(LogLevel.DEBUG, DefaultLoggerServiceImpl.class, null, "Log statement");

		loggerService.log(LogLevel.DEBUG, DefaultLoggerServiceImpl.class, null,
				"Log statement with param {0} and {1}", "123", "XYZ");

		
	}
	
	/**
	 * Test logging of error
	 */
	@Test
	@Loggable(LogLevel.OFF)
	public void testLogError() {
	    loggerService.log(LogLevel.ERROR, DefaultLoggerServiceImpl.class, new Exception("Test Error Logger"),
                "Log statement with param {0} and {1}", "123", "XYZ");
	}

}
