/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.service;

import gov.fda.open.demo.model.enums.LogLevel;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Default logger service implementation
 * 
 */
@Service
public class DefaultLoggerServiceImpl implements LoggerService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.fda.open.demo.service.LoggerService#isLogLevel(gov.fda.open.demo.
	 * model.enums.LogLevel, java.lang.Class)
	 */
	@Override
	public boolean isLogLevel(LogLevel logLevel, Class<?> clazz) {

		boolean result = false;

		switch (logLevel) {
    		case DEBUG:
    			result = getLogger(clazz).isDebugEnabled();
    			break;
    
    		case ERROR:
    			result = getLogger(clazz).isErrorEnabled();
    			break;
    		case INFO:
    			result = getLogger(clazz).isInfoEnabled();
    			break;
    		case TRACE:
    			result = getLogger(clazz).isTraceEnabled();
    			break;
    
    		case WARN:
    			result = getLogger(clazz).isWarnEnabled();
    			break;
    		default:
    			result = false;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.fda.open.demo.service.LoggerService#log(gov.fda.open.demo.model.enums
	 * .LogLevel, java.lang.Class, java.lang.Throwable, java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public void log(LogLevel logLevel, Class<?> clazz, Throwable throwable, String pattern,
			Object... arguments) {

		boolean loggable = isLogLevel(logLevel, clazz);

		switch (logLevel) {
    		case DEBUG:
    			if (loggable) {
    				debug(clazz, throwable, pattern, arguments);
    			}
    			break;
    		case ERROR:
    			if (loggable) {
    				error(clazz, throwable, pattern, arguments);
    			}
    			break;
    		case INFO:
    			if (loggable) {
    				info(clazz, throwable, pattern, arguments);
    			}
    			break;
    		case TRACE:
    			if (loggable) {
    				trace(clazz, throwable, pattern, arguments);
    			}
    			break;
    		case WARN:
    			if (loggable) {
    				warn(clazz, throwable, pattern, arguments);
    			}
    			break;
    		default:
    			break;
		}
	}

	/**
	 * Debug.
	 *
	 * @param clazz
	 *            the clazz
	 * @param throwable
	 *            the throwable
	 * @param pattern
	 *            the pattern
	 * @param arguments
	 *            the arguments
	 */
	private void debug(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {

		if (throwable != null) {
			getLogger(clazz).debug(format(pattern, arguments), throwable);
		} else {
			getLogger(clazz).debug(format(pattern, arguments));
		}
	}

	/**
	 * Error.
	 *
	 * @param clazz
	 *            the clazz
	 * @param throwable
	 *            the throwable
	 * @param pattern
	 *            the pattern
	 * @param arguments
	 *            the arguments
	 */
	private void error(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {

		if (throwable != null) {
			getLogger(clazz).error(format(pattern, arguments), throwable);
		} else {
			getLogger(clazz).error(format(pattern, arguments));
		}
	}

	/**
	 * Info.
	 *
	 * @param clazz
	 *            the clazz
	 * @param throwable
	 *            the throwable
	 * @param pattern
	 *            the pattern
	 * @param arguments
	 *            the arguments
	 */
	private void info(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {
		if (throwable != null) {
			getLogger(clazz).info(format(pattern, arguments), throwable);
		} else {
			getLogger(clazz).info(format(pattern, arguments));
		}
	}

	/**
	 * Trace.
	 *
	 * @param clazz
	 *            the clazz
	 * @param throwable
	 *            the throwable
	 * @param pattern
	 *            the pattern
	 * @param arguments
	 *            the arguments
	 */
	private void trace(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {

		if (throwable != null) {
			getLogger(clazz).trace(format(pattern, arguments), throwable);
		} else {
			getLogger(clazz).trace(format(pattern, arguments));
		}
	}

	/**
	 * Warn.
	 *
	 * @param clazz
	 *            the clazz
	 * @param throwable
	 *            the throwable
	 * @param pattern
	 *            the pattern
	 * @param arguments
	 *            the arguments
	 */
	private void warn(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {

		if (throwable != null) {
			getLogger(clazz).warn(format(pattern, arguments), throwable);
		} else {
			getLogger(clazz).warn(format(pattern, arguments));
		}
	}

	/**
	 * Format.
	 *
	 * @param pattern
	 *            the pattern
	 * @param arguments
	 *            the arguments
	 * @return the string
	 */
	private String format(String pattern, Object... arguments) {
		return MessageFormat.format(pattern, arguments);
	}

	/**
	 * Gets the logger.
	 *
	 * @param clazz
	 *            the clazz
	 * @return the logger
	 */
	private Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}
}
