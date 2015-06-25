/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.model.enums;

/**
 * The Enum Gender a concrete type for <i>patient.patientsex</i> attribute.
 * 
 * @see <a
 *      href="https://open.fda.gov/drug/event/reference/#patient.patientsex">Open
 *      FDA reference</a>
 */
public enum Gender {

    /** The Male. */
    Male("1"),
    /** The Female. */
    Female("2"),
    /** The Unknown. */
    Unknown("0");

    /** The field. */
    private String code;

    /**
     * Instantiates a new gender.
     *
     * @param code
     *            the code
     */
    Gender(String code) {
        this.code = code;
    }

    /**
     * Get display name given the gender code
     *
     * @param genderCode
     *            the gender code
     * @return the string
     */
    public static String displayName(String genderCode) {
        String resolved = null;
        if (genderCode != null) {
            for (Gender gender : Gender.values()) {
                if (gender.code.equals(genderCode)) {
                    resolved = gender.name();
                    break;
                }
            }
        }

        return resolved;
    }
}
