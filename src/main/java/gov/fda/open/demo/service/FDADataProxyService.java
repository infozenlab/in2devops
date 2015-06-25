/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.service;

import java.util.List;

import gov.fda.open.demo.model.request.GetDrugAdverseSummaryRequest;
import gov.fda.open.demo.model.response.GetDrugAdverseSummaryResponse;

import org.springframework.stereotype.Component;

/**
 * Proxy service implementation class to communicate with <b>FDA Open data
 * set</b> <b>RESTFul</b> service
 *
 * @see <a href="https://open.fda.gov/api/reference/">Open FDA</a>
 */
@Component
public interface FDADataProxyService {

    /**
     * Get the drug names that start with given parameter value in sorted order.
     *
     * @param partialDrugName
     *            the partial drug name
     * @return Collection Drugs names with sort order A-Z
     */
    public List<String> getDrugNames(String partialDrugName);

    /**
     * Get summary of <b>Adverse Drug Events</b> reported with the given
     * criteria specified in the request {@link GetDrugAdverseSummaryRequest}
     * object.
     * 
     * @param request
     *            the request
     * @return the drug adverse summary
     */
    public GetDrugAdverseSummaryResponse getDrugAdverseSummary(GetDrugAdverseSummaryRequest request);
}
