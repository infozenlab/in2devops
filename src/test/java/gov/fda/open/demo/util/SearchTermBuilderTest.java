/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.util;

import static org.junit.Assert.assertEquals;
import gov.fda.open.demo.error.ApplicationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for {@link SearchTermBuilder}.
 */
public class SearchTermBuilderTest {

	/** The search term builder. */
	private SearchTermBuilder searchTermBuilder;

	/**
	 * Sets the up.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@Before
	public void setUp() throws ApplicationException {
		searchTermBuilder = new SearchTermBuilder();
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
	 * Test append term.
	 */
	@Test
	public void testAppendTerm() {
		searchTermBuilder.appendTerm("patient.drug.medicinalproduct", "test");
		assertEquals("patient.drug.medicinalproduct:\"test\"", searchTermBuilder.toString());
	}

	/**
	 * Test and.
	 */
	@Test
	public void testAnd() {
		searchTermBuilder.appendTerm("patient.drug.medicinalproduct", "test").and()
				.appendTerm("primarysource.reportercountry", "US");

		assertEquals("patient.drug.medicinalproduct:\"test\"+AND+primarysource.reportercountry:\"US\"",
				searchTermBuilder.toString());
	}

	/**
	 * Test between.
	 */
	@Test
	public void testBetween() {
		searchTermBuilder.appendTerm("patient.drug.medicinalproduct", "test").and()
				.between("receivedate", "20140622", "20150622");
		assertEquals("patient.drug.medicinalproduct:\"test\"+AND+receivedate:[20140622+TO+20150622]",
				searchTermBuilder.toString());
	}

	/**
	 * Test exists.
	 */
	@Test
	public void testExists() {
		searchTermBuilder.exists("primarysource.reportercountry").and()
				.appendTerm("patient.drug.medicinalproduct", "test");
		assertEquals("_exists_:primarysource.reportercountry+AND+patient.drug.medicinalproduct:\"test\"",
				searchTermBuilder.toString());
	}

	/**
	 * Test group.
	 */
	@Test
	public void testGroup() {
		searchTermBuilder
				.appendTerm("patient.drug.medicinalproduct", "cetirizine loratadine diphenhydramine").group()
				.and().appendTerm("serious", "2");
		assertEquals(
				"(patient.drug.medicinalproduct:\"cetirizine+loratadine+diphenhydramine\")+AND+serious:\"2\"",
				searchTermBuilder.toString());
	}

}
