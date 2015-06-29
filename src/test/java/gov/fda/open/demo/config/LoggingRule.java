/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.config;

import gov.fda.open.demo.service.loggable.Loggable;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

/**
 * JUnit test class to turn off logging, to enable masking of logging
 * 
 */
public class LoggingRule extends TestWatcher {

    /** The logger. */
    private Logger logger;

    /** The logging level. */
    private Level loggingLevel;

    /**
     * Instantiates a new turn off logging rule.
     *
     * @param clazz the clazz
     */
    public LoggingRule(Class<?> clazz) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        logger = loggerContext.getLogger(clazz);
        loggingLevel = logger.getLevel();
    }

    /* (non-Javadoc)
     * @see org.junit.rules.TestWatcher#starting(org.junit.runner.Description)
     */
    @Override
    public void starting(Description desc) {
        Loggable annotation = desc.getAnnotation(Loggable.class);
        if (annotation != null) {
            Level level = Level.toLevel(annotation.value().name());
            logger.setLevel(level);
        }
    }

    /* (non-Javadoc)
     * @see org.junit.rules.TestWatcher#finished(org.junit.runner.Description)
     */
    @Override
    public void finished(Description desc) {
        logger.setLevel(loggingLevel);
    }
}
