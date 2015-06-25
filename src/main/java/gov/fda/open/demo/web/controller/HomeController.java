/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.web.controller;

import gov.fda.open.demo.model.enums.LogLevel;
import gov.fda.open.demo.model.enums.SummaryType;
import gov.fda.open.demo.model.request.GetDrugAdverseSummaryRequest;
import gov.fda.open.demo.model.response.GetDrugAdverseSummaryResponse;
import gov.fda.open.demo.service.FDADataProxyService;
import gov.fda.open.demo.service.loggable.Loggable;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Main controller class to handler user request
 */
@Controller
public class HomeController {

    /** The fda data service. */
    @Inject
    private FDADataProxyService fdaDataService;

    /**
     * Index.
     *
     * @param principal
     *            the principal
     * @return the string
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Loggable(LogLevel.INFO)
    public String index(Principal principal) {
        return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
    }

    /**
     * Gets the adverse summary.
     *
     * @param drugName
     *            the _drug name
     * @param summaryType
     *            the _summary type
     * @param startDate
     *            the _start date
     * @param endDate
     *            the _end date
     * @param maxFetchCnt
     *            the _max fetch cnt
     * @return the adverse summary
     */
    @Loggable(LogLevel.INFO)
    @Secured({ "USER", "ADMIN" })
    @RequestMapping(value = "/drug/summary", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody GetDrugAdverseSummaryResponse getAdverseSummary(
            @RequestParam(value = "drugName") String drugName,
            @RequestParam(value = "type", required = false, defaultValue = "REPORTER_COUNTRY") SummaryType summaryType,
            @RequestParam(value = "startDate", required = false) Date startDate,
            @RequestParam(value = "endDate", required = false) Date endDate,
            @RequestParam(value = "maxfetch", required = false, defaultValue = "100") Integer maxFetchCnt) {
        // TODO Validation
        GetDrugAdverseSummaryRequest request = new GetDrugAdverseSummaryRequest(drugName, summaryType,
                startDate, endDate, maxFetchCnt);

        GetDrugAdverseSummaryResponse response = fdaDataService.getDrugAdverseSummary(request);
        // Make empty aggregate field to zero
        response.makeEmptyFieldZero();

        return response;
    }

    /**
     * Gets the drug names.
     *
     * @param drugName
     *            the _drug name
     * @return the drug names
     */
    @Loggable(LogLevel.INFO)
    @Secured({ "USER", "ADMIN" })
    @RequestMapping(value = "/drug/names", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody List<String> getDrugNames(@RequestParam(value = "drugName") String drugName) {

        return fdaDataService.getDrugNames(drugName);
    }

}
