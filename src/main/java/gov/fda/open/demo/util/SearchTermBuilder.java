/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.util;

import static gov.fda.open.demo.util.StringUtil.*;
import static gov.fda.open.demo.config.Constants.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class SearchTermBuilder.
 */
public class SearchTermBuilder {
	
	/** The search terms. */
	private StringBuilder searchTerms = new StringBuilder();
	
	/**
	 * Append term.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the search term builder
	 */
	public SearchTermBuilder appendTerm(String key, String value) {
		
		String eValue = escapeQueryTerm(value);
		searchTerms.append(key).append(":").append(eValue);
		
		return this;
	}
	
	/**
	 * And.
	 *
	 * @return the search term builder
	 */
	public SearchTermBuilder and() {
		searchTerms.append("+AND+");
		
		return this;
	}
	
	/**
	 * Between.
	 *
	 * @param field the field
	 * @param min the min
	 * @param max the max
	 * @return the search term builder
	 */
	public SearchTermBuilder between(String field, Object min, Object max) {
		String from, to;
		if (min instanceof Date && max instanceof Date) {
			DateFormat df = new SimpleDateFormat(DF_YYYY_MM_DD);
			
			from = df.format(min);
			to = df.format(max);
		} else {
			from = (String) min;
			to = (String) max;
		}
		
		searchTerms.append(field).append(":").append("[")
		.append(from)
		   .append('+').append("TO").append('+')
		   .append(to).append(']');
		
		return this;
	}
	
	/**
	 * Exists.
	 *
	 * @param field the field
	 * @return the search term builder
	 */
	public SearchTermBuilder exists(String field) {
		searchTerms.append("_exists_").append(":").append(field);
		
		return this;
	}
	
	/**
	 * Group.
	 *
	 * @return the search term builder
	 */
	public SearchTermBuilder group() {
		searchTerms.insert(0, '(').append(')');
		
		return this;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return searchTerms.toString();
	}
	
}
