/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.web.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.fda.open.demo.config.LoggingRule;
import gov.fda.open.demo.config.WebSecurityConfigurationAware;
import gov.fda.open.demo.error.ApplicationException;
import gov.fda.open.demo.model.enums.LogLevel;
import gov.fda.open.demo.service.loggable.Loggable;
import gov.fda.open.demo.web.ExceptionHandler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

/**
 * JUnit test class for {@link HomeController}.
 */
public class HomeControllerTest extends WebSecurityConfigurationAware {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(HomeController.class);

	/** The logging rule */
	@Rule
	public TestRule loggingRule = new LoggingRule(ExceptionHandler.class);

	/**
	 * Test index.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testIndex() throws ApplicationException {
		try {
			mockMvc.perform(get("/"))
					.andExpect(view().name("home/homeNotSignedIn"))
					.andExpect(
							content()
									.string(allOf(
											containsString("<title>Login into MedEvent Application</title>"),
											containsString("<a href=\"/signin\">Sign in</a>"))));
		} catch (Exception e) {
			LOG.error("Error while running HomeController Test", e);
		}
	}

	/**
	 * Test get adverse summary.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@Test
	public void testGetAdverseSummary() throws ApplicationException {
		try {
			mockMvc.perform(
					get("/drug/summary").with(user("user")).param("drugName",
							"ASPRIN"))
					.andExpect(status().isOk())
					.andExpect(
							content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.success").value(true));
		} catch (Exception e) {
			LOG.error("Error while running HomeController Test", e);
		}
	}

	/**
	 * Test get adver summary bad request.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@Test
	@Loggable(LogLevel.OFF)
	public void testGetAdverSummaryBadRequest() throws ApplicationException {
		try {
			mockMvc.perform(get("/drug/summary").with(user("user")))
					.andExpect(status().isBadRequest())
					.andExpect(
							content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.success").value(false));
		} catch (Exception e) {
			LOG.error("Error while running HomeController Test", e);
		}
	}

	/**
	 * Test get drug names.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@Test
	public void testGetDrugNames() throws ApplicationException {
		try {
			mockMvc.perform(
					get("/drug/names").with(user("user")).param("drugName",
							"ASPRIN"))
					.andExpect(status().isOk())
					.andExpect(
							content().contentType(MediaType.APPLICATION_JSON));
		} catch (Exception e) {
			LOG.error("Error while running HomeController Test", e);
		}
	}

	/**
	 * Test get drug names bad request.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@Test
	@Loggable(LogLevel.OFF)
	public void testGetDrugNamesBadRequest() throws ApplicationException {
		try {
			mockMvc.perform(get("/drug/names").with(user("user")))
					.andExpect(status().isBadRequest())
					.andExpect(
							content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.success").value(false));
		} catch (Exception e) {
			LOG.error("Error while running HomeController Test", e);
		}
	}

}
