/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.model.request;

import gov.fda.open.demo.model.enums.SummaryType;
import gov.fda.open.demo.util.DateUtil;

import java.util.Date;

/**
 * Input request class for {#link {@link FDADataProxyService#getDrugAdverseSummary(GetDrugAdverseSummaryRequest)} API
 * 
 */
public class GetDrugAdverseSummaryRequest {

	/** The drug name. */
	private String drugName;

	/** The start date. */
	private Date startDate;

	/** The end date. */
	private Date endDate;

	/** The summary type. */
	private SummaryType summaryType;
	
	/** The max fetch cnt. */
	private Integer maxFetchCnt;

	/**
	 * Instantiates a new gets the drug adverse summary request.
	 */
	GetDrugAdverseSummaryRequest() {
	}

	/**
	 * Instantiates a new gets the drug adverse summary request.
	 *
	 * @param _drugName the _drug name
	 */
	public GetDrugAdverseSummaryRequest(String _drugName) {
		this(_drugName, null, null, null, null);
	}

	/**
	 * Instantiates a new gets the drug adverse summary request.
	 *
	 * @param drugName the _drug name
	 * @param summaryType the _summary type
	 * @param startDate the _start date
	 * @param endDate the _end date
	 * @param maxFetchCnt the _max fetch cnt
	 */
	public GetDrugAdverseSummaryRequest(String drugName, SummaryType summaryType, Date startDate,
			Date endDate, Integer maxFetchCnt) {
		this.drugName = drugName;
		this.summaryType = (summaryType != null) ? summaryType : SummaryType.REPORTER_COUNTRY;
		this.endDate = (endDate != null) ? endDate : new Date();
		this.startDate = (startDate != null) ? startDate : DateUtil.toTwelveMonthPrior(this.endDate);
		this.maxFetchCnt = (maxFetchCnt != null) ? maxFetchCnt : 500;
	}

	/**
	 * Gets the drug name.
	 *
	 * @return the drug name
	 */
	public String getDrugName() {
		return drugName;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Gets the summary type.
	 *
	 * @return the summary type
	 */
	public SummaryType getSummaryType() {
		return summaryType;
	}
	
	/**
	 * Gets the max fetch cnt.
	 *
	 * @return the max fetch cnt
	 */
	public int getMaxFetchCnt() {
		return maxFetchCnt;
	}

}
