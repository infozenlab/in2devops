/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.error;

import java.io.Serializable;

/**
 * A checked Exception class, used when exception that needs to be handled by 
 * caller
 */
public class BusinessException extends Exception implements ExceptionMessager {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The error code. */
	private final String errorCode;
	
	/** The values. */
	private final Serializable[] values;

	/**
	 * Instantiates a new business exception.
	 *
	 * @param errorCode the error code
	 * @param values the values
	 */
	public BusinessException(String errorCode, Serializable... values) {
		this.errorCode = errorCode;
		this.values = values;
	}

	/* (non-Javadoc)
	 * @see gov.fda.open.demo.error.ExceptionMessager#getErrorCode()
	 */
	@Override
	public String getErrorCode() {
		return errorCode;
	}

	/* (non-Javadoc)
	 * @see gov.fda.open.demo.error.ExceptionMessager#getValues()
	 */
	@Override
	public Object[] getValues() {
		return values;
	}
}
