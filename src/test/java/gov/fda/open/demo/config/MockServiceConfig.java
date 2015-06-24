/**
 * *********************************************************************
 * Copyright (c) 2015 InfoZen, Inc. All rights reserved. InfoZen
 * PROPRIETARY/CONFIDENTIAL. Usage is subject to license terms.
 * *********************************************************************
 */
package gov.fda.open.demo.config;

import gov.fda.open.demo.model.DrugFrequency;
import gov.fda.open.demo.model.request.GetDrugAdverseSummaryRequest;
import gov.fda.open.demo.model.response.GetDrugAdverseSummaryResponse;
import gov.fda.open.demo.service.FDADataProxyServiceImpl;
import gov.fda.open.demo.service.FDADataProxyService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Test configuration for Service configuration.
 */
@Configuration
public class MockServiceConfig extends ServiceConfig {

	/**
	 * Fda data service.
	 *
	 * @return the IFDA data proxy service
	 */
	@Bean
	@Profile("test")
	public FDADataProxyService fdaDataService() {
		return new FDADataProxyServiceImpl() {

			@Override
			public GetDrugAdverseSummaryResponse getDrugAdverseSummary(GetDrugAdverseSummaryRequest request) {
				DrugFrequency[] frequency = new DrugFrequency[1];
				Map<String, Integer> summary = new HashMap<String, Integer>();
				frequency[0] = new DrugFrequency(new Date(), summary);
				return new GetDrugAdverseSummaryResponse(true, null, null, frequency);
			}
			
		};
	}
}
