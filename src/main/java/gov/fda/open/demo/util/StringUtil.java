/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.util;

import gov.fda.open.demo.error.ApplicationException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Utility class implements helper method related to manipulating String object
 * 
 */
public abstract class StringUtil {

	private StringUtil() {
	}
	
	/**
	 * Encode.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String encode(String value) {
		if (value == null) {
			return value;
		}

		String newValue = value;
		try {
			newValue = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException ue) {
			throw new ApplicationException(ue);
		}
		return newValue;
	}

	/**
	 * Escape query term.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String escapeQueryTerm(String value) {
		if (value == null) {
			return null;
		}

		final StringBuilder buf = new StringBuilder(encode(value));
		buf.insert(0, '"');
		buf.append('"');
		return buf.toString();
	}
	
	/**
	 * Find first.
	 *
	 * @param values the values
	 * @param prefix the prefix
	 * @return the int
	 */
	public static int findFirst(String[] values, String prefix) {
		int first = 0;
		int last = values.length;
		
		if (first == last) {
			return -1;
		}
		int middle = (first + last) / 2;
		// Find middle is greater than zero
		while (middle > 0) {
			// If middle value start with prefix then
			if (values[middle].startsWith(prefix)) {
				// Check if there is not hit prior
				if (middle == 0 || !values[middle - 1].startsWith(prefix)) {
					return middle;
				} else {
					last = middle - 1;
				}
		    // If prefix is values is greater than then first would be second half
			// Otherwise search 1 half	
			} else if (prefix.compareTo(values[middle]) > 0) {
				first = middle + 1;
			} else {
				last = middle - 1;
			}
			
			middle = (first + last)/2;
			// if first is greater than last or length then we reached 
			// end of search
			if (first > last || first == values.length) {
				middle = -1;
				break;
			}
			
		}
		return middle;
	}

}
