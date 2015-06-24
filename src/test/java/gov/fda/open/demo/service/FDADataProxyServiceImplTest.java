/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import gov.fda.open.demo.model.request.GetDrugAdverseSummaryRequest;
import gov.fda.open.demo.model.response.GetDrugAdverseSummaryResponse;
import gov.fda.open.demo.util.SearchTermBuilder;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * JUnit class for {@link FDADataProxyServiceImpl}, use mocking (<a
 * href="http://mockito.org/">Mockito</a>) for class to <b>openFDA Restful
 * service</a> .
 */
@RunWith(MockitoJUnitRunner.class)
public class FDADataProxyServiceImplTest {

	/** The fda data proxy service. */
	@InjectMocks
	private FDADataProxyServiceImpl fdaDataProxyService;

	/** The template. */
	@Mock
	private RestTemplate template;

	/** The json string. */
	private String jsonString;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		ReflectionTestUtils.setField(fdaDataProxyService, "uri", "https://api.fda.gov");
		ReflectionTestUtils.setField(fdaDataProxyService, "appKey", "testKey123");
		ReflectionTestUtils.setField(fdaDataProxyService, "limit", 100);

		// Setup
		URL fileURL = getClass().getClassLoader().getResource("DrugResultsAggregatorEventTestData.json");

		jsonString = FileUtils.readFileToString(new File(fileURL.toURI()));
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test get unique drug names.
	 */
	@Test
	public void testGetUniqueDrugNames() {
		List<String> drugs = fdaDataProxyService.getDrugNames("AB");
		assertFalse(drugs.isEmpty());

		drugs = fdaDataProxyService.getDrugNames("ab");
		assertFalse(drugs.isEmpty());
	}

	/**
	 * Test get drug adverse summary.
	 */
	@SuppressWarnings("serial")
	@Test
	public void testGetDrugAdverseSummary() {
		// Execute
		GetDrugAdverseSummaryRequest request = new GetDrugAdverseSummaryRequest("ROCEPHIN");

		// Setup Mock
		SearchTermBuilder searchParamBuilder = new SearchTermBuilder();
		searchParamBuilder.exists(request.getSummaryType().getField()).and()
				.appendTerm("patient.drug.medicinalproduct", request.getDrugName()).and()
				.between("receivedate", request.getStartDate(), request.getEndDate());
		// build query String
		String searchTerm = searchParamBuilder.toString();

		StringBuilder drugURL = new StringBuilder("https://api.fda.gov");
		drugURL.append(FDADataProxyServiceImpl.DRUG_EVENT_SEARCH_URL);

		final Map<String, String> params = new HashMap<String, String>();
		params.put("searchTerm", searchTerm);
		params.put("appKey", "testKey123");
		params.put("limit", String.valueOf(100));
		params.put("skip", String.valueOf(0));

		URI httpUri = UriComponentsBuilder.fromHttpUrl(drugURL.toString()).buildAndExpand(params).toUri();

		when(template.getForObject(httpUri, String.class)).thenReturn(jsonString);

		// Execute
		GetDrugAdverseSummaryResponse response = fdaDataProxyService.getDrugAdverseSummary(request);

		// Verify
		assertNotNull(response);
		assertTrue(response.isSuccess());
		assertNull(response.getMessage());

		// Execute for message
		request = new GetDrugAdverseSummaryRequest("ROCEPHIN", null, null, null, 10);
		response = fdaDataProxyService.getDrugAdverseSummary(request);

		// Verify
		assertNotNull(response);
		assertTrue(response.isSuccess());
		assertNotNull(response.getMessage());

		// / Test case when HttpClientException is throw
		when(template.getForObject(httpUri, String.class)).thenThrow(
				new HttpClientErrorException(HttpStatus.NOT_FOUND) {
					@Override
					public String getResponseBodyAsString() {
						return "{ \"error\": { \"code\": \"NOT_FOUND\", \"message\": \"No matches found!\" } }";
					}
				});

		request = new GetDrugAdverseSummaryRequest("ROCEPHIN");
		response = fdaDataProxyService.getDrugAdverseSummary(request);
		assertNotNull(response);
		assertFalse(response.isSuccess());
		assertNotNull(response.getErrorCode());
		assertNotNull(response.getMessage());

	}
}
