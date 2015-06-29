/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import gov.fda.open.demo.config.WebSecurityConfigurationAware;
import gov.fda.open.demo.error.ApplicationException;
import gov.fda.open.demo.service.FDADataProxyServiceImpl;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * The Class UserAuthenticationIntegrationTest.
 */
public class UserAuthenticationIntegrationTest extends
		WebSecurityConfigurationAware {

	/** The sec context attr. */
	private static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(UserAuthenticationIntegrationTest.class);

	/**
	 * Requires authentication.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@Test
	public void requiresAuthentication() throws ApplicationException {
		try {
			mockMvc.perform(get("/account/current")).andExpect(
					redirectedUrl("http://localhost/signin"));
		} catch (Exception e) {
			LOG.error(
					"Error while running UserAuthenticationIntegrationTest Test",
					e);
		}
	}

	/**
	 * User authenticates.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@Test
	public void userAuthenticates() throws ApplicationException {
		final String username = "user";
		ResultMatcher matcher = new ResultMatcher() {
			public void match(MvcResult mvcResult) throws ApplicationException {
				HttpSession session = mvcResult.getRequest().getSession();
				SecurityContext securityContext = (SecurityContext) session
						.getAttribute(SEC_CONTEXT_ATTR);
				Assert.assertEquals(securityContext.getAuthentication()
						.getName(), username);
			}
		};
		try {
			mockMvc.perform(
					post("/j_spring_security_check").param("j_username",
							username).param("j_password", "demo"))
					.andExpect(redirectedUrl("/")).andExpect(matcher);
		} catch (Exception e) {
			LOG.error(
					"Error while running UserAuthenticationIntegrationTest Test",
					e);
		}
	}

	/**
	 * User authentication fails.
	 *
	 * @throws ApplicationException
	 *             the exception
	 */
	@Test
	public void userAuthenticationFails() throws ApplicationException {
		final String username = "user";
		try {
			mockMvc.perform(
					post("/j_spring_security_check").param("j_username",
							username).param("j_password", "invalid"))
					.andExpect(redirectedUrl("/signin?error=1"))
					.andExpect(new ResultMatcher() {
						public void match(MvcResult mvcResult)
								throws ApplicationException {
							HttpSession session = mvcResult.getRequest()
									.getSession();
							SecurityContext securityContext = (SecurityContext) session
									.getAttribute(SEC_CONTEXT_ATTR);
							Assert.assertNull(securityContext);
						}
					});
		} catch (Exception e) {
			LOG.error(
					"Error while running UserAuthenticationIntegrationTest Test",
					e);
		}
	}
}
