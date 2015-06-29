/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.fda.open.demo.error.ApplicationException;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for {@link StringUtilTest}
 */
public class StringUtilTest {

	/**
	 * Sets the up.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@Before
	public void setUp() throws ApplicationException {

		// Nothing to do since nothing to setup
	}

	/**
	 * Tear down.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@After
	public void tearDown() throws ApplicationException {

		// Nothing to do since nothing to teardown
	}

	/**
	 * Test encode.
	 */
	@Test
	public void testEncode() {
		assertNull(StringUtil.encode(null));
		assertEquals("Test+123", StringUtil.encode("Test 123"));
	}

	/**
	 * Test escape query term.
	 */
	@Test
	public void testEscapeQueryTerm() {
		assertNull(StringUtil.escapeQueryTerm(null));
		assertEquals("\"Test+123\"", StringUtil.escapeQueryTerm("Test 123"));
	}

	/**
	 * Test find first.
	 */
	@Test
	public void testFindFirst() {
		String[] values = { "Test", "JUnit", "Exception", "API", "REST",
				"Testing" };

		Arrays.sort(values);

		assertEquals(-1, StringUtil.findFirst(values, "t"));
		assertEquals(-1, StringUtil.findFirst(values, "TE"));
		assertEquals(4, StringUtil.findFirst(values, "Test"));
		assertEquals(2, StringUtil.findFirst(values, "JUnit"));
		assertEquals(1, StringUtil.findFirst(values, "Excep"));
		assertEquals(0, StringUtil.findFirst(values, "API"));
		assertEquals(5, StringUtil.findFirst(values, "Testing"));

		String[] values1 = {};
		assertEquals(-1, StringUtil.findFirst(values1, "t"));

	}

}
