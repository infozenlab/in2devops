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
import gov.fda.open.demo.config.WebSecurityConfigurationAware;

import org.junit.Test;
import org.springframework.http.MediaType;

/**
 * JUnit test class for {@link HomeController}
 * 
 */
public class HomeControllerTest extends WebSecurityConfigurationAware {

	/**
	 * Test index.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testIndex() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(view().name("home/homeNotSignedIn"))
				.andExpect(
						content().string(
								allOf(containsString("<title>Login into MedEvent Application</title>"),
										containsString("<a href=\"/signin\">Sign in</a>"))));
	}

	/**
	 * Test get adverse summary.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetAdverseSummary() throws Exception {
		mockMvc.perform(get("/drug/summary").with(user("user")))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.success").value(false));
		
		mockMvc.perform(get("/drug/summary").with(user("user")).param("drugName", "ASPRIN"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").value(true));
	}

	/**
	 * Test get drug names.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetDrugNames() throws Exception {
		mockMvc.perform(get("/drug/names").with(user("user")))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").value(false));
		
		mockMvc.perform(get("/drug/names").with(user("user")).param("drugName", "ASPRIN"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
