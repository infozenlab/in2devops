/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.util;

import gov.fda.open.demo.model.enums.Gender;
import gov.fda.open.demo.model.enums.OrgType;
import gov.fda.open.demo.model.enums.SummaryType;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Helper class to aggregate RESTFul response result from <b>openFDA Drug
 * events</b>
 * 
 */
public class DrugResultsAggregator {

    /** The aggregate. */
    private Map<Object, Object> aggregate = new HashMap<Object, Object>();

    /** The summary type. */
    private SummaryType summaryType;

    /**
     * Instantiates a new drug results aggregator.
     *
     * @param summaryType
     *            the summary type
     */
    public DrugResultsAggregator(SummaryType summaryType) {
        this.summaryType = summaryType;
    }

    /**
     * Aggregate results
     *
     * @param resultsNode
     *            the results node
     */
    public void aggregate(JsonNode resultsNode) {
        byReceivedDateFieldType(resultsNode);
    }

    /**
     * Gets the results.
     *
     * @param <X>
     *            the generic type
     * @return the results
     */
    @SuppressWarnings("unchecked")
    public <X> X getResults() {
        return (X) aggregate;
    }

    /**
     * By received date field type.
     *
     * @param resultsNode
     *            the results node
     */
    protected void byReceivedDateFieldType(JsonNode resultsNode) {
        Iterator<JsonNode> results = resultsNode.elements();

        while (results.hasNext()) {
            JsonNode result = results.next();
            String receivedate = result.get("receivedate").asText();
            Date rDate = DateUtil.toDate("yyyyMMdd", receivedate);
            rDate = DateUtil.toFirstOfMonth(rDate);

            // TODO Convert (Abbreviations (short form)) to long
            String value = readTermFieldValue(summaryType.getField(), result);
            if (summaryType.isByPatientGender()) {
                value = Gender.displayName(value);
            } else if (summaryType.isByRceiverType() || summaryType.isBySenderType()) {
                value = OrgType.displayName(value);
            }

            inc(rDate, value);
        }
    }

    /**
     * Inc.
     *
     * @param receiveDate
     *            the receive date
     * @param reactions
     *            the reactions
     */
    @SuppressWarnings("unchecked")
    protected void inc(Date receiveDate, String... reactions) {
        if (reactions != null) {
            Map<String, Integer> reactionsCnt = (Map<String, Integer>) aggregate.get(receiveDate);
            if (reactionsCnt == null) {
                reactionsCnt = new HashMap<String, Integer>();
                aggregate.put(receiveDate, reactionsCnt);
            }

            for (String reaction : reactions) {
                Integer cnt = reactionsCnt.get(reaction);
                if (cnt == null) {
                    cnt = 1;
                } else {
                    cnt++;
                }
                reactionsCnt.put(reaction, cnt);
            }
        }
    }

    /**
     * Read term field value.
     *
     * @param termField
     *            the term field
     * @param result
     *            the result
     * @return the string
     */
    private String readTermFieldValue(String termField, final JsonNode result) {

        int idx = termField.indexOf(".");
        if (idx > 0) {
            String field = termField.substring(0, idx);
            String nextTermField = termField.substring(idx + 1, termField.length());
            JsonNode jsonNode = result.get(field);
            if (jsonNode != null) {
                return readTermFieldValue(nextTermField, jsonNode);
            }
        }

        String reporterCountry = null;
        if (result != null) {
            reporterCountry = result.get(termField).asText();
        }
        return reporterCountry;
    }
}
