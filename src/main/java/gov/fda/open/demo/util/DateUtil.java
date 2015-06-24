/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class implements helper method related to {@link Date} class
 * 
 */
public abstract class DateUtil {

	private DateUtil() {
	}
	
	/**
	 * To first of month.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date toFirstOfMonth(Date date) {
		return covertDate(date, 1, null, null);
	}

	/**
	 * To year begin.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date toYearBegin(Date date) {
		return covertDate(date, 1, 0, null);
	}

	/**
	 * To six month prior.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date toSixMonthPrior(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		month = month - 6;
		if (month < 0) {
			month = month + 12;
			year = year - 1;
		}

		return covertDate(date, null, month, year);
	}

	/**
	 * To twelve month prior.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date toTwelveMonthPrior(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		year = year - 1;
		return covertDate(date, null, null, year);
	}

	/**
	 * Covert date.
	 *
	 * @param dateObj the date obj
	 * @param date the date
	 * @param month the month
	 * @param year the year
	 * @return the date
	 */
	public static Date covertDate(Date dateObj, Integer date, Integer month, Integer year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateObj);

		if (date != null && date > -1) {
			cal.set(Calendar.DATE, date);
		}

		if (month != null && month > -1) {
			cal.set(Calendar.MONTH, month);
		}

		if (year != null && year > -1) {
			cal.set(Calendar.YEAR, year);
		}

		return cal.getTime();
	}

	/**
	 * To date.
	 *
	 * @param patten the patten
	 * @param value the value
	 * @return the date
	 */
	public static Date toDate(String patten, String value) {
		DateFormat formater = new SimpleDateFormat(patten);

		try {
			return formater.parse(value);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}

	}

}
