/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.model.response;

/**
 * Base service response class.
 */
public abstract class BaseResponse {

	/** The success. */
	protected boolean success;

	/** The error code. */
	protected String errorCode;

	/** The message. */
	protected String message;

	/**
	 * Instantiates a new base response.
	 */
	BaseResponse() {
	}

	/**
	 * Instantiates a new base response.
	 *
	 * @param success the _success
	 * @param errorCode the _error code
	 * @param message the _message
	 */
	public BaseResponse(boolean success, String errorCode, String message) {
		this.success = success;
		this.errorCode = errorCode;
		this.message = message;
	}

	/**
	 * Checks if is success.
	 *
	 * @return true, if is success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
