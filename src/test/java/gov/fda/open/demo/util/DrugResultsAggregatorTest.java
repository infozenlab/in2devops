/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.util;

import static org.junit.Assert.assertTrue;
import gov.fda.open.demo.error.ApplicationException;
import gov.fda.open.demo.model.enums.SummaryType;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JUnit test class for {@link DrugResultsAggregator}.
 */
public class DrugResultsAggregatorTest {

	/** The results node. */
	private JsonNode resultsNode;
	
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final String DATE_1 = "20140401";
	private static final String DATE_2 = "20140301";

	/**
	 * Sets the up.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws ApplicationException, JsonProcessingException, IOException, URISyntaxException {
		// Setup
		URL fileURL = getClass().getClassLoader().getResource(
				"DrugResultsAggregatorEventTestData.json");

		ObjectMapper mapper = new ObjectMapper();
		resultsNode = mapper.readTree(new File(fileURL.toURI())).get("results");
	}

	/**
	 * Test by patient gender.
	 */
	@Test
	public void testByPatientGender() {

		DrugResultsAggregator drugResultAggregator = new DrugResultsAggregator(
				SummaryType.PATIENT_GENDER);
		// Execute
		drugResultAggregator.byReceivedDateFieldType(resultsNode);

		// Verify
		Map<Date, Map<String, Integer>> aggregateResults = drugResultAggregator
				.getResults();
		assertTrue(!aggregateResults.isEmpty());
		Map<String, Integer> reactions = aggregateResults.get(DateUtil.toDate(
				DATE_FORMAT, DATE_1));
		assertTrue(!reactions.isEmpty());

		reactions = aggregateResults.get(DateUtil
				.toDate(DATE_FORMAT, DATE_1));
		assertTrue(!reactions.isEmpty());
	}

	/**
	 * Test by receiver type.
	 */
	@Test
	public void testByReceiverType() {
		DrugResultsAggregator drugResultAggregator = new DrugResultsAggregator(
				SummaryType.RECEIVER_TYPE);
		// Execute
		drugResultAggregator.byReceivedDateFieldType(resultsNode);

		// Verify
		Map<Date, Map<String, Integer>> aggregateResults = drugResultAggregator
				.getResults();

		assertTrue(!aggregateResults.isEmpty());
	}

	/**
	 * Test by sender type.
	 */
	@Test
	public void testBySenderType() {
		DrugResultsAggregator drugResultAggregator = new DrugResultsAggregator(
				SummaryType.SENDER_TYPE);
		// Execute
		drugResultAggregator.byReceivedDateFieldType(resultsNode);

		// Verify
		Map<Date, Map<String, Integer>> aggregateResults = drugResultAggregator
				.getResults();

		assertTrue(!aggregateResults.isEmpty());
	}

	/**
	 * Test by country.
	 */
	@Test
	public void testByCountry() {
		DrugResultsAggregator drugResultAggregator = new DrugResultsAggregator(
				SummaryType.REPORTER_COUNTRY);

		// Execute
		drugResultAggregator.byReceivedDateFieldType(resultsNode);

		// Verify
		Map<Date, Map<String, Integer>> aggregateResults = drugResultAggregator
				.getResults();
		assertTrue(!aggregateResults.isEmpty());
		Map<String, Integer> countries = aggregateResults.get(DateUtil.toDate(
				DATE_FORMAT, DATE_2));
		assertTrue(countries.size() > 0);

		countries = aggregateResults.get(DateUtil
				.toDate(DATE_FORMAT, DATE_1));
		assertTrue(countries.size() > 0);

	}

}
