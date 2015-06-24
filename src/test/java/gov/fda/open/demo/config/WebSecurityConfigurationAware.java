/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.config;

import org.junit.Before;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Base security aware class for JUnit testing {@link Controller} with security enabled
 * 
 */
public abstract class WebSecurityConfigurationAware extends WebAppConfigurationAware {

    /** The spring security filter chain. */
    @Inject
    private FilterChainProxy springSecurityFilterChain;

    /* (non-Javadoc)
     * @see gov.fda.open.demo.config.WebAppConfigurationAware#before()
     */
    @Before
    public void before() {
        this.mockMvc = webAppContextSetup(this.wac)
                .addFilters(this.springSecurityFilterChain).build();
    }
}
