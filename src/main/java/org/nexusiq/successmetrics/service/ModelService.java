package org.nexusiq.successmetrics.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nexusiq.successmetrics.model.DataPoint;
import org.nexusiq.successmetrics.model.MTTRPoint;
import org.nexusiq.successmetrics.model.PolicyViolation;
import org.nexusiq.successmetrics.model.SummaryDataPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelService {
		
	
	@Autowired 
	private CalculatorService calculator;
	
	@Autowired 
	private SQLService sqlService;
	
	@Autowired 
	private UtilityService utilityService;
	
	public Map<String, Object> setSecurityReportModel() {
		
		Map<String, Object> map = new HashMap<>();

	    map.put("criticalSecurityViolationsData", sqlService.executeSQLMetrics(SQLStatement.CriticalSecurityViolations));
	    map.put("severeSecurityViolationsData", sqlService.executeSQLMetrics(SQLStatement.SevereSecurityViolations));
	    map.put("moderateSecurityViolationsData", sqlService.executeSQLMetrics(SQLStatement.ModerateSecurityViolations));
	    map.put("discoveredSecurityViolationsData", sqlService.executeSQLMetrics(SQLStatement.DiscoveredSecurityViolations));
	    map.put("fixedSecurityViolationsData", sqlService.executeSQLMetrics(SQLStatement.FixedSecurityViolations));   
	    map.put("waivedSecurityViolationsData", sqlService.executeSQLMetrics(SQLStatement.WaivedSecurityViolations));
	    map.put("openSecurityViolationsData", sqlService.executeSQLMetrics(SQLStatement.OpenSecurityViolationsTrend));
	    map.put("securityViolationsData", sqlService.executeSQLMetrics(SQLStatement.SecurityViolations));
	    
	    return map;
	}
	
	public Map<String, Object> setLicenseReportModel() {
		
		Map<String, Object> map = new HashMap<>();

		map.put("criticalLicenseViolationsData", sqlService.executeSQLMetrics(SQLStatement.CriticalLicenseViolations));
		map.put("severeLicenseViolationsData", sqlService.executeSQLMetrics(SQLStatement.SevereLicenseViolations));		
		map.put("moderateLicenseViolationsData", sqlService.executeSQLMetrics(SQLStatement.ModerateLicenseViolations));	
		map.put("discoveredLicenseViolationsData", sqlService.executeSQLMetrics(SQLStatement.DiscoveredLicenseViolations));
		map.put("fixedLicenseViolationsData", sqlService.executeSQLMetrics(SQLStatement.FixedLicenseViolations));  
		map.put("waivedLicenseViolationsData", sqlService.executeSQLMetrics(SQLStatement.WaivedLicenseViolations));	
		map.put("openLicenseViolationsData", sqlService.executeSQLMetrics(SQLStatement.OpenLicenseViolationsTrend));
	    map.put("licenseViolationsData", sqlService.executeSQLMetrics(SQLStatement.LicenseViolations));
	    
	    return map;
	}
	
	
	public Map<String, Object> setApplicationsReportModel() {
		
		Map<String, Object> map = new HashMap<>();
		
		String latestTimePeriod = utilityService.latestPeriod();
	    List<DataPoint> applicationViolationsData = sqlService.executeSQLMetrics(sqlService.AddWhereClauseAppOpenViolations(SQLStatement.ApplicationsOpenViolations, latestTimePeriod, "APPLICATION_NAME"));
	    List<DataPoint> organisationViolationsData = sqlService.executeSQLMetrics(sqlService.AddWhereClauseOrgOpenViolations(SQLStatement.OrganisationsOpenViolations, latestTimePeriod, "ORGANIZATION_NAME"));
	
		map.put("applicationsOnboardedData", sqlService.executeSQLMetrics(SQLStatement.ApplicationsOnboarded));
		map.put("numberOfScansData", sqlService.executeSQLMetrics(SQLStatement.NumberOfScans));
		map.put("applicationScansData", sqlService.executeSQLMetrics(SQLStatement.ApplicationScans));			
		map.put("mostCriticalOrganisationsData", organisationViolationsData);
		map.put("mostCriticalApplicationsData", applicationViolationsData);
		map.put("mostScannedApplicationsData", sqlService.executeSQLMetrics(SQLStatement.MostScannedApplications));
		map.put("mttrData", sqlService.executeSQLMTTR(SQLStatement.MTTR));
		map.put("applicationsSecurityStatusData", sqlService.executeSQLMetrics(SQLStatement.ApplicationsSecurityStatus));
		map.put("applicationsLicenseStatusData", sqlService.executeSQLMetrics(SQLStatement.ApplicationsLicenseStatus));

	    return map;
	}

	
	public Map<String, Object> setSummaryModel() throws ParseException {
		
		Map<String, Object> map = new HashMap<>();
		
		String timePeriod = utilityService.getTimePeriod();
		
		map.put("timePeriod", timePeriod);
		
		map.put("applicationsOnboardedAvg", calculator.applicationsOnboardedAverage(sqlService.executeSQLMetrics(SQLStatement.ApplicationsOnboarded)));
		map.put("numberOfScansAvg", calculator.sumAndAveragePointA(sqlService.executeSQLMetrics(SQLStatement.NumberOfScans)));
		map.put("applicationScansAvg", calculator.sumAndAveragePointA(sqlService.executeSQLMetrics(SQLStatement.ApplicationScans)));

		// Security 
		
		int discoveredSecurityViolations = calculator.sumAllPoints(sqlService.executeSQLMetrics(SQLStatement.DiscoveredSecurityViolations));
		int fixedSecurityViolations = calculator.sumAllPoints(sqlService.executeSQLMetrics(SQLStatement.FixedSecurityViolations));
		int waivedSecurityViolations = calculator.sumAllPoints(sqlService.executeSQLMetrics(SQLStatement.WaivedSecurityViolations));
		int discoveredCriticalSecurityViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.DiscoveredSecurityViolations), "A");
		int discoveredSevereSecurityViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.DiscoveredSecurityViolations), "B");
		int discoveredModerateSecurityViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.DiscoveredSecurityViolations), "C");
		int fixedCriticalSecurityViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.FixedSecurityViolations), "A");
		int fixedSevereSecurityViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.FixedSecurityViolations), "B");
		int fixedModerateSecurityViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.FixedSecurityViolations), "C");
		int waivedCriticalSecurityViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.WaivedSecurityViolations), "A");
		int waivedSevereSecurityViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.WaivedSecurityViolations), "B");
		int waivedModerateSecurityViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.WaivedSecurityViolations), "C");
		
		map.put("countDiscoveredSecurityViolations", discoveredSecurityViolations);
	    map.put("countFixedSecurityViolations", fixedSecurityViolations);
	    map.put("countWaivedSecurityViolations", waivedSecurityViolations);
	    map.put("countDiscoveredCriticalSecurityViolations", discoveredCriticalSecurityViolations);
	    map.put("countDiscoveredSevereSecurityViolations", discoveredSevereSecurityViolations);
	    map.put("countDiscoveredModerateSecurityViolations", discoveredModerateSecurityViolations);
	    map.put("countFixedCriticalSecurityViolations", fixedCriticalSecurityViolations);
	    map.put("countFixedSevereSecurityViolations", fixedSevereSecurityViolations);
	    map.put("countFixedModerateSecurityViolations", fixedModerateSecurityViolations);
	    map.put("countWaivedCriticalSecurityViolations", waivedCriticalSecurityViolations);
	    map.put("countWaivedSevereSecurityViolations", waivedSevereSecurityViolations);
	    map.put("countWaivedModerateSecurityViolations", waivedModerateSecurityViolations);
	    
	    // License
	    
	    int discoveredLicenseViolations = calculator.sumAllPoints(sqlService.executeSQLMetrics(SQLStatement.DiscoveredLicenseViolations));
	    int fixedLicenseViolations = calculator.sumAllPoints(sqlService.executeSQLMetrics(SQLStatement.FixedLicenseViolations));
	    int waivedLicenseViolations = calculator.sumAllPoints(sqlService.executeSQLMetrics(SQLStatement.WaivedLicenseViolations));
	    int discoveredCriticalLicenseViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.DiscoveredLicenseViolations), "A");
	    int discoveredSevereLicenseViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.DiscoveredLicenseViolations), "B");
	    int discoveredModerateLicenseViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.DiscoveredLicenseViolations), "C");
	    int fixedCriticalLicenseViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.FixedLicenseViolations), "A");
	    int fixedSevereLicenseViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.FixedLicenseViolations), "B");
	    int fixedModerateLicenseViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.FixedLicenseViolations), "C");
	    int waivedCriticalLicenseViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.WaivedLicenseViolations), "A");
	    int waivedSevereLicenseViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.WaivedLicenseViolations), "B");
	    int waivedModerateLicenseViolations = calculator.sumPoint(sqlService.executeSQLMetrics(SQLStatement.WaivedLicenseViolations), "C");

	    map.put("countDiscoveredLicenseViolations", discoveredLicenseViolations);
	    map.put("countFixedLicenseViolations", fixedLicenseViolations);
	    map.put("countWaivedLicenseViolations", waivedLicenseViolations);
	    map.put("countDiscoveredCriticalLicenseViolations", discoveredCriticalLicenseViolations);
	    map.put("countDiscoveredSevereLicenseViolations", discoveredSevereLicenseViolations);
	    map.put("countDiscoveredModerateLicenseViolations", discoveredModerateLicenseViolations);
	    map.put("countFixedCriticalLicenseViolations", fixedCriticalLicenseViolations);
	    map.put("countFixedSevereLicenseViolations", fixedSevereLicenseViolations);
	    map.put("countFixedModerateLicenseViolations", fixedModerateLicenseViolations);
	    map.put("countWaivedCriticalLicenseViolations", waivedCriticalLicenseViolations);
	    map.put("countWaivedSevereLicenseViolations", waivedSevereLicenseViolations);
	    map.put("countWaivedModerateLicenseViolations", waivedModerateLicenseViolations);
	    
	    // Fix rate
	    
	    int discovered = discoveredSecurityViolations + discoveredLicenseViolations;
		int fixed = fixedSecurityViolations + fixedLicenseViolations;
		int waived = waivedSecurityViolations + waivedLicenseViolations;

	    float reducedRisk = (((float)(fixed + waived)/discovered) * 100);
	    
	    map.put("reducedRisk", String.format("%.02f", reducedRisk));
	    
	    // Applications
		String latestTimePeriod = utilityService.latestPeriod();
	    List<DataPoint> applicationViolationsData = sqlService.executeSQLMetrics(sqlService.AddWhereClauseAppOpenViolations(SQLStatement.ApplicationsOpenViolations, latestTimePeriod, "APPLICATION_NAME"));

	    
		map.put("applicationViolationsData", applicationViolationsData);
	    map.put("applicationCriticalViolationsAvg", calculator.sumAndAveragePointA(applicationViolationsData));
	    map.put("mostCriticalApplication", new SummaryDataPoint(applicationViolationsData.get(0).getLabel(), (int) (applicationViolationsData.get(0).getPointA())));
	    map.put("leastCriticalApplication", new SummaryDataPoint(applicationViolationsData.get(applicationViolationsData.size()-1).getLabel(), (int) (applicationViolationsData.get(applicationViolationsData.size()-1).getPointA())));
	    	    
	    
	    // MTTR
	    
	    List<Float> pointA = new ArrayList<>();	
	    List<Float> pointB = new ArrayList<>();	
	    List<Float> pointC = new ArrayList<>();	
	    
	    for (MTTRPoint dp : sqlService.executeSQLMTTR(SQLStatement.MTTR)) {
	    	pointA.add(dp.getPointA());
	    	pointB.add(dp.getPointB());
	    	pointC.add(dp.getPointC());
		}
	    
	    map.put("mttrCriticalAvg", String.format("%.02f", calculator.averagePoint(pointA)));
	    map.put("mttrSevereAvg", String.format("%.02f", calculator.averagePoint(pointB)));
	    map.put("mttrModerateAvg", String.format("%.02f", calculator.averagePoint(pointC)));	
	    
		return map;
		
	}
	

}
