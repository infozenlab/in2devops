/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * JUnit test class for {@link DateUtil}.
 * 
 */
public class DateUtilTest {

	/**
	 * Test to first of month.
	 */
	@Test
	public void testToFirstOfMonth() {
		Date date = DateUtil.toFirstOfMonth(DateUtil.toDate("yyyyMMdd", "20140313"));
		
		Calendar dateTime = Calendar.getInstance();
		dateTime.setTime(date);
		
		assertEquals(2014, dateTime.get(Calendar.YEAR));
		assertEquals(2, dateTime.get(Calendar.MONTH));
		assertEquals(1, dateTime.get(Calendar.DATE));
	}
	
	/**
	 * Test to six month prior.
	 */
	@Test
	public void testToSixMonthPrior() {
		Date date = DateUtil.toDate("yyyyMMdd", "20140313");
		Date newDate = DateUtil.toSixMonthPrior(date);
		
		Calendar dateTime = Calendar.getInstance();
		dateTime.setTime(newDate);
		int newMonth = dateTime.get(Calendar.MONTH);
		int outYear = dateTime.get(Calendar.YEAR);
		
		assertEquals(8, newMonth);
		assertEquals(2013, outYear);
		
		date = DateUtil.toDate("yyyyMMdd", "20141213");
		newDate = DateUtil.toSixMonthPrior(date);
		
		dateTime = Calendar.getInstance();
		dateTime.setTime(newDate);
		newMonth = dateTime.get(Calendar.MONTH);
		 outYear = dateTime.get(Calendar.YEAR);
		
		assertEquals(5, newMonth);
		assertEquals(2014, outYear);
		
	}
	
	/**
	 * Test to twelve month prior.
	 */
	@Test
	public void testToTwelveMonthPrior() {
		Date date = DateUtil.toDate("yyyyMMdd", "20140313");
		Date newDate = DateUtil.toTwelveMonthPrior(date);
		
		Calendar dateTime = Calendar.getInstance();
		dateTime.setTime(newDate);
		int newMonth = dateTime.get(Calendar.MONTH);
		int outYear = dateTime.get(Calendar.YEAR);
		
		assertEquals(2, newMonth);
		assertEquals(2013, outYear);
		
		date = DateUtil.toDate("yyyyMMdd", "20141213");
		newDate = DateUtil.toTwelveMonthPrior(date);
		
		dateTime = Calendar.getInstance();
		dateTime.setTime(newDate);
		newMonth = dateTime.get(Calendar.MONTH);
		 outYear = dateTime.get(Calendar.YEAR);
		
		assertEquals(11, newMonth);
		assertEquals(2013, outYear);
		
	}
	
	/**
	 * Test to year begin.
	 */
	@Test
	public void testToYearBegin() {
		Date date = DateUtil.toYearBegin(DateUtil.toDate("yyyyMMdd", "20140313"));
		
		Calendar dateTime = Calendar.getInstance();
		dateTime.setTime(date);
		
		assertEquals(2014, dateTime.get(Calendar.YEAR));
		assertEquals(0, dateTime.get(Calendar.MONTH));
		assertEquals(1, dateTime.get(Calendar.DATE));
	}

	/**
	 * Test to date.
	 */
	@Test
	public void testToDate() {
		Date date = DateUtil.toDate("yyyyMMdd", "20140313");
		Calendar dateTime = Calendar.getInstance();
		dateTime.setTime(date);
		
		assertEquals(2014, dateTime.get(Calendar.YEAR));
		assertEquals(2, dateTime.get(Calendar.MONTH));
		assertEquals(13, dateTime.get(Calendar.DATE));
	}

}
