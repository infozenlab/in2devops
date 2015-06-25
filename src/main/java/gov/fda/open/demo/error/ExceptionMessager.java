/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.error;

/**
 * The base interface ExceptionMessager class to expose <i>errorCode</i> and
 * values associated to build build message
 */
public interface ExceptionMessager {

    /**
     * Gets the error code (message properties).
     *
     * @return the error code
     */
    public String getErrorCode();

    /**
     * Gets the values for value substitute
     *
     * @return the values
     */
    public Object[] getValues();
}
