package org.sonatype.cs.metrics.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
	
	@Autowired
	private PeriodsDataService periodsDataService;
	     
	@Autowired
	private SecurityDataService securityDataService;
	     
	@Autowired
	private LicenseDataService licenseDataService;

	@Autowired
	private ApplicationsDataService applicationsDataService;
	     
	@Autowired
	private SummaryTotalsDataService summaryTotalsDataService;
	
	
	public Map<String, Object> getMetrics(String tableName, Map<String, Object> periodsData) throws ParseException{
		
		Map<String, Object> model = new HashMap<>();

		//Map<String, Object> periodsData = periodsDataService.getPeriodData(tableName);
	    Map<String, Object> applicationData = applicationsDataService.getApplicationData(tableName, periodsData);
	    Map<String, Object> securityViolationsData = securityDataService.getSecurityViolations(tableName);
	    Map<String, Object> licenseViolationsData = licenseDataService.getLicenseViolations(tableName);
	    Map<String, Object> summaryTotals = summaryTotalsDataService.getSummaryData(tableName);
	
		//model.putAll(periodsData);
		model.putAll(applicationData);
		model.putAll(securityViolationsData);
		model.putAll(licenseViolationsData);
		model.putAll(summaryTotals);

		return model;
	}
	
}
