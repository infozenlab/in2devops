/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.model.enums;

/**
 * The Enum OrgType. A concrete type which represents <i>sender.sendertype</i>
 * and <i>receiver.receivertype</i>.
 * 
 * @see <a href="https://open.fda.gov/drug/event/reference/#patient.patientsex">Open FDA reference</a>
 */
public enum OrgType {

	/** The pharmaceutial. */
	PHARMACEUTIAL("1", "Pharmaceutical Company"),
	
	/** The regulatoary authority. */
	REGULATOARY_AUTHORITY("2", "Regulatory Authority"),
	
	/** The health professional. */
	HEALTH_PROFESSIONAL("3", "Health Professional"),
	
	/** The reginal pharma center. */
	REGINAL_PHARMA_CENTER("4", "Regional Pharmacovigilance Center"),
	
	/** The who. */
	WHO("5", "WHO Collaborating Center for International Drug Monitoring"),
	
	/** The Other. */
	Other("6", "Other");
	
	/** The code. */
	private String code;
	
	/** The display value. */
	private String displayValue;

	/**
	 * Instantiates a new org type.
	 *
	 * @param code the code
	 * @param displayValue the display value
	 */
	OrgType(String code, String displayValue) {
		this.code = code;
		this.displayValue = displayValue;
	}
	
	/**
	 * Get display name given the org code
	 *
	 * @param orgCode the org code
	 * @return the string
	 */
	public static String displayName(String orgCode) {
		String resolved = null;
		if (orgCode != null) {
			for(OrgType type : OrgType.values()) {
				if(type.code.equals(orgCode)) {
					resolved = type.displayValue;
					break;
				}
			}
		}
		
		return resolved;
	}
}
