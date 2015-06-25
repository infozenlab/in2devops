/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;

/**
 * Default <b>Spring AOP Advice</b>class to handler Exception {@link Exception}
 * and {@link ServletRequestBindingException}.
 * 
 */
@ControllerAdvice
class ExceptionHandler {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * Handle exceptions thrown by handlers.
     *
     * @param exception
     *            the exception
     * @param request
     *            the request
     * @return the model and view
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception exception) {
        LOG.error("Unhandled error", exception);

        ModelAndView modelAndView = new ModelAndView("error/general");
        Throwable thowable = Throwables.getRootCause(exception);
        modelAndView.addObject("errorMessage", thowable);
        return modelAndView;
    }

    /**
     * Missing request parameter.
     *
     * @param exception
     *            the exception
     * @param request
     *            the request
     * @return the map
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ServletRequestBindingException.class)
    public @ResponseBody Map<String, Object> missingRequestParameter(Exception exception) {
        LOG.error("Bad request missing binding value", exception);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("success", false);
        response.put("error", 400);
        response.put("message", exception.getMessage());

        return response;
    }

}