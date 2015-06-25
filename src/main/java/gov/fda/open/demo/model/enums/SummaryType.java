/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.model.enums;

/**
 * The Enum SummaryType, type of summaries supported. The adverse drug events
 * returned per summary type
 * 
 * @see <a
 *      href="https://open.fda.gov/drug/event/reference/#patient.patientsex">Open
 *      FDA reference</a>
 */
public enum SummaryType {

    /** The reporter country. */
    REPORTER_COUNTRY("primarysource.reportercountry"),

    /** The patient gender. */
    PATIENT_GENDER("patient.patientsex"),

    /** The sender type. */
    SENDER_TYPE("sender.sendertype"),

    /** The receiver type. */
    RECEIVER_TYPE("receiver.receivertype");

    /** The summary field. */
    private String field;

    /**
     * Instantiates a new summary type.
     *
     * @param field
     *            the _field
     */
    SummaryType(String field) {
        this.field = field;
    }

    /**
     * Gets the field.
     *
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * Checks if is reporter country.
     *
     * @return true, if is reporter country
     */
    public boolean isReporterCountry() {
        return compareTo(REPORTER_COUNTRY) == 0;
    }

    /**
     * Checks if is by patient gender.
     *
     * @return true, if is by patient gender
     */
    public boolean isByPatientGender() {
        return compareTo(PATIENT_GENDER) == 0;
    }

    /**
     * Checks if is by sender type.
     *
     * @return true, if is by sender type
     */
    public boolean isBySenderType() {
        return compareTo(SENDER_TYPE) == 0;
    }

    /**
     * Checks if is by rceiver type.
     *
     * @return true, if is by rceiver type
     */
    public boolean isByRceiverType() {
        return compareTo(RECEIVER_TYPE) == 0;
    }
}
