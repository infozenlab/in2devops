/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.fda.open.demo.model.enums.LogLevel;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for {@link DefaultLoggerServiceImpl}
 */
public class DefaultLoggerServiceImplTest {

	/** The logger service. */
	private LoggerService loggerService;

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

		loggerService.log(LogLevel.ERROR, DefaultLoggerServiceImpl.class, new Exception("Test Error Logger"),
				"Log statement with param {0} and {1}", "123", "XYZ");
	}

}
