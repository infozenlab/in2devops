/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Test configuration for {@link SecurityConfig}
 */
public class MockSecurityConfig extends SecurityConfig {

	/* (non-Javadoc)
	 * @see gov.fda.open.demo.config.SecurityConfig#csrfMatcher()
	 */
	@Profile("test")
	@Override
	@Bean
	public RequestMatcher csrfMatcher() {
		return new RequestMatcher() {

			@Override
			public boolean matches(HttpServletRequest request) {
				return false;
			}
		};
	}
}
