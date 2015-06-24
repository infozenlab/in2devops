/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.config;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Base test class for JUnit testing.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = {MockServiceConfig.class, MockSecurityConfig.class, WebMvcConfig.class})
public abstract class WebAppConfigurationAware {

    /** The wac. */
    @Inject
    protected WebApplicationContext wac;
    
    /** The mock mvc. */
    protected MockMvc mockMvc;

    /**
     * Before.
     */
    @Before
    public void before() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

}
