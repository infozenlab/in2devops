/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.model.response;

import gov.fda.open.demo.model.DrugFrequency;
import gov.fda.open.demo.model.request.GetDrugAdverseSummaryRequest;
import gov.fda.open.demo.service.FDADataProxyService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Output response class for {#link {@link FDADataProxyService#getDrugAdverseSummary(GetDrugAdverseSummaryRequest)} API.
 * 
 */
public class GetDrugAdverseSummaryResponse extends BaseResponse {

	/** The drug frequencies. */
	private DrugFrequency[] drugFrequencies;

	/**
	 * Instantiates a new gets the drug adverse summary response.
	 */
	GetDrugAdverseSummaryResponse() {
		super();
	}

	/**
	 * Instantiates a new gets the drug adverse summary response.
	 *
	 * @param success the _success
	 * @param errorCode the _error code
	 * @param message the _message
	 * @param drugFrequencies the _drug frequencies
	 */
	public GetDrugAdverseSummaryResponse(boolean success, String errorCode, String message,
			DrugFrequency[] drugFrequencies) {
		super(success, errorCode, message);
		this.drugFrequencies = drugFrequencies;
	}

	/**
	 * Gets the drug frequencies.
	 *
	 * @return the drug frequencies
	 */
	public DrugFrequency[] getDrugFrequencies() {
		return drugFrequencies;
	}

	/**
	 * Make empty field zero.
	 */
	public void makeEmptyFieldZero() {
		if (success) {
			Collection<String> fields = getAllDrugFreqFields();
			for (DrugFrequency freq : drugFrequencies) {
				Map<String, Integer> drugFreqFields = freq.getFrequency();
				for (String field : fields) {
					if (drugFreqFields.get(field) == null) {
						drugFreqFields.put(field, 0);
					}
				}
			}
		}
	}

	/**
	 * Gets the all drug freq fields.
	 *
	 * @return the all drug freq fields
	 */
	private Collection<String> getAllDrugFreqFields() {
		Set<String> fields = new HashSet<String>();
		for (DrugFrequency freq : drugFrequencies) {
			fields.addAll(freq.getFrequency().keySet());
		}

		return fields;
	}

}
