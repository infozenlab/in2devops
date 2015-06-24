/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.config;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Spring SecurityConfig for the application.
 * 
 * <p><b>Note:</b> Currently configured to use in memory users</p>
 * 
 */
@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Override this method to configure {@link WebSecurity}. For
	 * example, if you wish to ignore certain requests.
	 *
	 * @param web the web
	 * @throws Exception the exception
	 */
	@Override
    public void configure(WebSecurity web) throws Exception {
		web
	      .ignoring()
	         .antMatchers("/resources/**");
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().requireCsrfProtectionMatcher(csrfMatcher())
			.and()
				.formLogin()
        			.loginPage("/signin")
        			.loginProcessingUrl("/j_spring_security_check")
        			.usernameParameter("j_username")
        			.passwordParameter("j_password")
        			.failureHandler(authenticationFailureHandler())
        	.and()
        		.logout()
        			.logoutUrl("/logout")
        	.and()
        		.authorizeRequests()
        			// Everybody has access to this URL "anonymous"
        			.antMatchers("/", "/signin", "/signup",
        					"/error", "/unknownerror")
    					.permitAll()
    				.antMatchers("/**").authenticated()
    				// All request has to be authenticated
    				.anyRequest().authenticated() 		
        	.and()
        		.exceptionHandling().accessDeniedPage("/accessdenied");
        		
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("user").password("demo").roles("USER")
				.and()
				.withUser("admin").password("admin").roles("ADMIN");
	}

	/**
	 * Authentication failure handler.
	 *
	 * @return the authentication failure handler
	 */
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		ExceptionMappingAuthenticationFailureHandler securityExceptionTranslationHandler = new ExceptionMappingAuthenticationFailureHandler();
		
		final Map<String, String> failureUrlMap = new HashMap<>();
		failureUrlMap.put(CredentialsExpiredException.class.getCanonicalName(), "/newPassword");
        securityExceptionTranslationHandler.setExceptionMappings(failureUrlMap);
        securityExceptionTranslationHandler.setDefaultFailureUrl("/signin?error=1");
		
		return securityExceptionTranslationHandler;
	}
	
	/**
	 * Password encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	/**
	 * Csrf matcher.
	 *
	 * @return the request matcher
	 */
	@Bean(name = "csrfMatcher")
	public RequestMatcher csrfMatcher() {
		return new RequestMatcher() {
			private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
			
			@Override
			public boolean matches(HttpServletRequest request) {
				return !allowedMethods.matcher(request.getMethod()).matches();
			}
		};
	}
}