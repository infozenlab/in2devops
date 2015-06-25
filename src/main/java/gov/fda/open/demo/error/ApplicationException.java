/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.error;

/**
 * A unchecked <code>Exception</code> class, thrown when System is not usable.
 */
public class ApplicationException extends RuntimeException implements ExceptionMessager {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new application exception.
     *
     * @param th
     *            the th
     */
    public ApplicationException(Throwable th) {
        super(th);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.fda.open.demo.error.ExceptionMessager#getErrorCode()
     */
    @Override
    public String getErrorCode() {
        return "unknown.server.error";
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.fda.open.demo.error.ExceptionMessager#getValues()
     */
    @Override
    public Object[] getValues() {
        return new String[] { getMessage() };
    }
}
