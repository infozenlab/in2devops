/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.service;

import org.springframework.stereotype.Component;

import gov.fda.open.demo.model.enums.LogLevel;

/**
 * The Interface LoggerService.
 */
@Component
public interface LoggerService {

    /**
     * Checks if is log level.
     *
     * @param logLevel
     *            the log level
     * @param clazz
     *            the clazz
     * @return true, if is log level
     */
    boolean isLogLevel(LogLevel logLevel, Class<?> clazz);

    /**
     * Log.
     *
     * @param logLevel
     *            the log level
     * @param clazz
     *            the clazz
     * @param throwable
     *            the throwable
     * @param pattern
     *            the pattern
     * @param arguments
     *            the arguments
     */
    void log(LogLevel logLevel, Class<?> clazz, Throwable throwable, String pattern, Object... arguments);
}
