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
import gov.fda.open.demo.config.LoggingRule;
import gov.fda.open.demo.error.ApplicationException;
import gov.fda.open.demo.model.enums.LogLevel;
import gov.fda.open.demo.model.request.GetDrugAdverseSummaryRequest;
import gov.fda.open.demo.model.response.GetDrugAdverseSummaryResponse;
import gov.fda.open.demo.service.loggable.Loggable;
import gov.fda.open.demo.util.SearchTermBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
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
	
	/** The Constant URI_KEY. */
	private static final String URI_KEY = "uri";
	
	/** The Constant URI_VALUE. */
	private static final String URI_VALUE = "https://api.fda.gov";
	
	/** The Constant APP_KEY. */
	private static final String APP_KEY = "appKey";
	
	/** The Constant APP_VALUE. */
	private static final String APP_VALUE = "testKey123";
	
	/** The Constant LIMIT_KEY. */
	private static final String LIMIT_KEY = "limit";
	
	/** The Constant RESOURCE. */
	private static final String RESOURCE = "DrugResultsAggregatorEventTestData.json";
	
	/** The Constant LIMIT_VALUE. */
	private static final Integer LIMIT_VALUE = 100;
	
	/** The Constant SKIP_KEY. */
	private static final String SKIP_KEY = "skip";
	
	/** The Constant SEARCH_KEY. */
	private static final String SEARCH_KEY = "searchTerm";
	
	/** The Constant DRUG_NAME_1. */
	private static final String DRUG_NAME_1 = "AB";
	
	/** The Constant DRUG_NAME_2. */
	private static final String DRUG_NAME_2 = "ROCEPHIN";
	
	/** The Constant MEDICINAL_PRODUCT. */
	private static final String MEDICINAL_PRODUCT = "patient.drug.medicinalproduct";
	
	/** The Constant RECEIVED_DATE. */
	private static final String RECEIVED_DATE = "receivedate";
	
    /** The logging rule. */
    @Rule
    public TestRule loggingRule = new LoggingRule(FDADataProxyServiceImpl.class);

	/**
	 * Sets the up.
	 *
	 * @throws ApplicationException
	 *             the exception
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@Before
	public void setUp() throws ApplicationException, IOException, URISyntaxException {
		ReflectionTestUtils.setField(fdaDataProxyService, URI_KEY, URI_VALUE);
		ReflectionTestUtils.setField(fdaDataProxyService, APP_KEY, APP_VALUE);
		ReflectionTestUtils.setField(fdaDataProxyService, LIMIT_KEY, LIMIT_VALUE);

		// Setup
		URL fileURL = getClass().getClassLoader().getResource(RESOURCE);

		jsonString = FileUtils.readFileToString(new File(fileURL.toURI()));
	}

	/**
	 * Tear down.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@After
	public void tearDown() throws ApplicationException {
		// Do nothing because WebService handles transaction and nothing to teardown.
	}

	/**
	 * Test get unique drug names.
	 */
	@Test
	public void testGetUniqueDrugNames() {
		List<String> drugs = fdaDataProxyService.getDrugNames(DRUG_NAME_1);
		assertFalse(drugs.isEmpty());

		drugs = fdaDataProxyService.getDrugNames(DRUG_NAME_1.toLowerCase());
		assertFalse(drugs.isEmpty());
	}

	/**
	 * Test get drug adverse summary.
	 */
	@Test
	public void testGetDrugAdverseSummary() {
		// Execute
		GetDrugAdverseSummaryRequest request = new GetDrugAdverseSummaryRequest(DRUG_NAME_2);

		// Setup Mock
		SearchTermBuilder searchParamBuilder = new SearchTermBuilder();
		searchParamBuilder.exists(request.getSummaryType().getField()).and()
				.appendTerm(MEDICINAL_PRODUCT, request.getDrugName()).and()
				.between(RECEIVED_DATE, request.getStartDate(), request.getEndDate());
		// build query String
		String searchTerm = searchParamBuilder.toString();

		StringBuilder drugURL = new StringBuilder(URI_VALUE);
		drugURL.append(FDADataProxyServiceImpl.DRUG_EVENT_SEARCH_URL);

		final Map<String, String> params = new HashMap<String, String>();
		params.put(SEARCH_KEY, searchTerm);
		params.put(APP_KEY, APP_VALUE);
		params.put(LIMIT_KEY, String.valueOf(100));
		params.put(SKIP_KEY, String.valueOf(0));

		URI httpUri = UriComponentsBuilder.fromHttpUrl(drugURL.toString()).buildAndExpand(params).toUri();

		when(template.getForObject(httpUri, String.class)).thenReturn(jsonString);

		// Execute
		GetDrugAdverseSummaryResponse response = fdaDataProxyService.getDrugAdverseSummary(request);

		// Verify
		assertNotNull(response);
		assertTrue(response.isSuccess());
		assertNull(response.getMessage());

		// Execute for message
		request = new GetDrugAdverseSummaryRequest(DRUG_NAME_2, null, null, null, 10);
		response = fdaDataProxyService.getDrugAdverseSummary(request);

		// Verify
		assertNotNull(response);
		assertTrue(response.isSuccess());
		assertNotNull(response.getMessage());

	}
	
	/**
	 * Test get drug summary not found.
	 */
	@SuppressWarnings("serial")
    @Test
    @Loggable(LogLevel.OFF)
    public void testGetDrugSummaryNotFound() {
	    
	    // Execute
        GetDrugAdverseSummaryRequest request = new GetDrugAdverseSummaryRequest(DRUG_NAME_2);

        // Setup Mock
        SearchTermBuilder searchParamBuilder = new SearchTermBuilder();
        searchParamBuilder.exists(request.getSummaryType().getField()).and()
                .appendTerm(MEDICINAL_PRODUCT, request.getDrugName()).and()
                .between(RECEIVED_DATE, request.getStartDate(), request.getEndDate());
        // build query String
        String searchTerm = searchParamBuilder.toString();

        StringBuilder drugURL = new StringBuilder(URI_VALUE);
        drugURL.append(FDADataProxyServiceImpl.DRUG_EVENT_SEARCH_URL);

        final Map<String, String> params = new HashMap<String, String>();
        params.put(SEARCH_KEY, searchTerm);
        params.put(APP_KEY, APP_VALUE);
        params.put(LIMIT_KEY, String.valueOf(100));
        params.put(SKIP_KEY, String.valueOf(0));

        URI httpUri = UriComponentsBuilder.fromHttpUrl(drugURL.toString()).buildAndExpand(params).toUri();
        
     // / Test case when HttpClientException is throw
        when(template.getForObject(httpUri, String.class)).thenThrow(
                new HttpClientErrorException(HttpStatus.NOT_FOUND) {
                    @Override
                    public String getResponseBodyAsString() {
                        return "{ \"error\": { \"code\": \"NOT_FOUND\", \"message\": \"No matches found!\" } }";
                    }
                });

        GetDrugAdverseSummaryResponse  response = fdaDataProxyService.getDrugAdverseSummary(request);
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getErrorCode());
        assertNotNull(response.getMessage());
	    
	}
}
