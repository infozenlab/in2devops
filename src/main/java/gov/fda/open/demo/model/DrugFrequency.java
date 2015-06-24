/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.model;

import java.util.Date;
import java.util.Map;

/**
 * The Class DrugFrequency.
 */
public class DrugFrequency {

	/** The date. */
	private Date date;

	/** The frequency. */
	private Map<String, Integer> frequency;

	/**
	 * Instantiates a new drug frequency.
	 */
	DrugFrequency() {
	}

	/**
	 * Instantiates a new drug frequency.
	 *
	 * @param reactionDate the _reaction date
	 * @param frequency the _frequency
	 */
	public DrugFrequency(Date reactionDate, Map<String, Integer> frequency) {
		this.date = reactionDate;
		this.frequency = frequency;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Gets the frequency.
	 *
	 * @return the frequency
	 */
	public Map<String, Integer> getFrequency() {
		return frequency;
	}
	
	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public Integer getTotal() {
		Integer total = 0;
		for(Integer freq : frequency.values()) {
			total += freq;
		}
		
		return total;
	}

}
