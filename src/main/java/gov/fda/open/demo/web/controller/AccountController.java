/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.web.controller;

import gov.fda.open.demo.model.enums.LogLevel;
import gov.fda.open.demo.service.loggable.Loggable;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Web controller class to handle events pertaining to user accessing
 * application.
 */
@Controller
public class AccountController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    /**
     * Accesss denied.
     *
     * @param principal the principal
     * @return the string
     */
    @RequestMapping(value = "accessdenied", method = { RequestMethod.GET, RequestMethod.POST })
    @Loggable(LogLevel.INFO)
    public String accesssDenied(Principal principal) {

        String userName = "Anonymous";
        if (principal != null) {
            userName = principal.getName();
        }

        // Which page ???
        LOG.warn("User {} denied access this page !!!", userName);

        return "error/accessdenied";

    }
}
