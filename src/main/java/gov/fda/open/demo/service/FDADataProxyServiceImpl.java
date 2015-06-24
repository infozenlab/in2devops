/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.service;

import gov.fda.open.demo.error.ApplicationException;
import gov.fda.open.demo.model.DrugFrequency;
import gov.fda.open.demo.model.enums.LogLevel;
import gov.fda.open.demo.model.enums.SummaryType;
import gov.fda.open.demo.model.request.GetDrugAdverseSummaryRequest;
import gov.fda.open.demo.model.response.GetDrugAdverseSummaryResponse;
import gov.fda.open.demo.service.loggable.Loggable;
import gov.fda.open.demo.util.DrugResultsAggregator;
import gov.fda.open.demo.util.SearchTermBuilder;
import gov.fda.open.demo.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation class for {@link FDADataProxyService}. Class acts as facade
 * implementation for <b>Open FDA Service Drugs Adverse Events</> 
 * 
 * <a href="https://open.fda.gov/api/reference/">openFDA</a>
 */
@Service
public class FDADataProxyServiceImpl implements FDADataProxyService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FDADataProxyServiceImpl.class);

	/** The Constant DRUG_EVENT_SEARCH_URL. */
	protected static final String DRUG_EVENT_SEARCH_URL = "/drug/event.json?api_key={appKey}&search={searchTerm}&limit={limit}&skip={skip}";

	/** The uri. */
	@Value("${fdadataset.uri:https://api.fda.gov}")
	private String uri;

	/** The app key. */
	@Value("${app_key:HLWyWIHnwvkPqiYm1wWdx0NcZH9ghBKzCBTeSjJG}")
	private String appKey;

	/** The limit. */
	@Value("${limitPerRequest:100}")
	private int limit;

	/** The template. */
	@Inject
	private RestTemplate template;

	/** The drug names. */
	private static String[] drugNames = null;
	
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Returns all the drug names that start with give drug name in sorted order
	 * 
	 * @see gov.fda.open.demo.service.IFDADataProxyService#getDrugNames(java.lang.String)
	 */
	@Loggable(LogLevel.INFO)
	@Override
	public List<String> getDrugNames(String partialDrugName) {

		try {
			if (drugNames == null) {
				// TOD Add constants
				URL url = Thread.currentThread().getContextClassLoader().getResource("fdadrugnames.txt");
				List<String> lines = FileUtils.readLines(new File(url.toURI()));
				Collections.sort(lines);
				drugNames = lines.toArray(new String[lines.size()]);
			}

			String upperDrugName = partialDrugName.toUpperCase();
			int idx = StringUtil.findFirst(drugNames, upperDrugName);
			List<String> uniqueDrugNames = new ArrayList<String>();
			// If greater than -1 then found something
			if (idx > -1) {
    			for (int i = idx; i <= drugNames.length; i++) {
    				if (drugNames[i].startsWith(upperDrugName) && uniqueDrugNames.size() < 10) {
    					uniqueDrugNames.add(drugNames[i]);
    				} else {
    					break;
    				}
    
    			}
			}
			return uniqueDrugNames;
		} catch (IOException | URISyntaxException ie) {
			throw new ApplicationException(ie);
		}

	}

	/**
	 * Queries <b>openFDA</b> using RESTFul Service.
	 * 
	 * @see <a href="https://open.fda.gov/drug/event/">Drug Adverse Events</a>
	 * @see gov.fda.open.demo.service.IFDADataProxyService#getDrugAdverseSummary(gov.fda.open.demo.model.request.GetDrugAdverseSummaryRequest)
	 */
	@Loggable(LogLevel.INFO)
	@Override
	public GetDrugAdverseSummaryResponse getDrugAdverseSummary(GetDrugAdverseSummaryRequest request) {

		// Build search term from the request object
		String searchTerm = buildSearchTerm(request);

		Map<String, String> params = new HashMap<String, String>();
		params.put("searchTerm", searchTerm);
		params.put("appKey", appKey);
		params.put("limit", String.valueOf(limit));

		boolean success = true;
		int skip = 0, total = 0;
		String errorCode = null, message = null;
		DrugFrequency[] reactionFrequencies = null;
		
		// Get the summary
		SummaryType summaryType = request.getSummaryType();
		// Max total records to be fetched
		int totalFetch = request.getMaxFetchCnt();
		DrugResultsAggregator drugResultAggregrator = new DrugResultsAggregator(summaryType);
		try {
			do {
				params.put("skip", String.valueOf(skip));
				// Build drug events URI
				URI httpUri = buildDrugEventsRequestURI(params);
				// Execute
				String responseBody = template.getForObject(httpUri, String.class);
				// Parse the JSON node
				JsonNode jRootNode = mapper.readTree(responseBody);
				if (jRootNode.has("error") == false) {
					total = jRootNode.get("meta").get("results").get("total").asInt(0);
				} else {
					JsonNode jErrorNode = jRootNode.get("error");
					errorCode = jErrorNode.get("code").asText();
					message = jErrorNode.get("message").asText();
					// Log the error
					success = false;
					break;
				}
				
				JsonNode resultsNode = jRootNode.get("results");
				drugResultAggregrator.aggregate(resultsNode);

				skip += limit;
			} while (total > skip && skip < totalFetch);

			// Get aggregated results
			Map<Date, Map<String, Integer>> aggregateResults = drugResultAggregrator.getResults();
			// Build the Drug frequency
			reactionFrequencies = new DrugFrequency[aggregateResults.size()];
			List<Date> dates = new ArrayList<Date>(aggregateResults.keySet());
			// Sort the date keys
			Collections.sort(dates);
			int i = 0;
			for (Date date : dates) {
				reactionFrequencies[i++] = new DrugFrequency(date, aggregateResults.get(date));
			}
		} catch (HttpClientErrorException e) {
			LOG.debug("Open FDA returned error", e);
			success = false;
			String jsonResponse = e.getResponseBodyAsString();
			LOG.error(" Exception response {} ", jsonResponse);
			try {
				JsonNode jErrorNode = mapper.readTree(jsonResponse).get("error");
				errorCode = jErrorNode.get("code").asText();
				message = jErrorNode.get("message").asText();
			} catch (IOException ex) {
				LOG.error("Client side error", ex);
				errorCode = "404";
				message = "Json parser error";
			}
		} catch (IOException | RestClientException e) {
			LOG.error("Error occurred", e);
			throw new ApplicationException(e);
		}

		GetDrugAdverseSummaryResponse response = new GetDrugAdverseSummaryResponse(success, errorCode,
				message, reactionFrequencies);
		if (reactionFrequencies != null && total > totalFetch) {
			response.setMessage(String.format("You are viewing \"%d\" of \"%d\" results", totalFetch, total));
		}

		return response;
	}
	
	/**
	 * Helper method to build search term
	 * 
	 * @param request
	 * @return
	 */
	private String buildSearchTerm(GetDrugAdverseSummaryRequest request) {
		
		SummaryType summaryType = request.getSummaryType();
		
		SearchTermBuilder searchParamBuilder = new SearchTermBuilder();

		searchParamBuilder.exists(summaryType.getField()).and()
				.appendTerm("patient.drug.medicinalproduct", request.getDrugName()).and()
				.between("receivedate", request.getStartDate(), request.getEndDate());

		// build query String
		return searchParamBuilder.toString();
	}
	
	/**
	 * Helper method to build DrugEvetn Restful URI
	 * 
	 * @param params
	 * @return
	 */
	private URI buildDrugEventsRequestURI(final Map<String, String> params) {
		StringBuilder drugURL = new StringBuilder(uri);
		drugURL.append(DRUG_EVENT_SEARCH_URL);

		URI httpUri = UriComponentsBuilder.fromHttpUrl(drugURL.toString()).buildAndExpand(params).toUri();

		LOG.debug(" URI : {}", httpUri);
		
		return httpUri;

	}
}
