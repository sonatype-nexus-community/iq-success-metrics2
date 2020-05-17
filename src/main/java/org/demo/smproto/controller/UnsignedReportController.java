package org.demo.smproto.controller;

import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.SummaryDataPoint;
import org.demo.smproto.service.CalculatorService;
import org.demo.smproto.service.IDataService;
import org.demo.smproto.service.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UnsignedReportController {
	
	@Autowired 
	private IDataService dataService;
	
	@Autowired 
	private CalculatorService calculator;
	
	private static final Logger log = LoggerFactory.getLogger(UnsignedReportController.class);
	
	@GetMapping({"/unsignedReport"})
	public String unsignedReport(Model model) {
		
		
		// Summary
		
		String timePeriod = "week";
		
		model.addAttribute("timePeriod", timePeriod);

		model.addAttribute("applicationsOnboardedAvg", calculator.applicationsOnboardedAverage(dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationsOnboarded))));
		
		model.addAttribute("numberOfScansAvg", calculator.sumAndAveragePointA(dataService.getDataPoints(dataService.executeSQL(SQLStatement.NumberOfScans))));
		
		model.addAttribute("applicationScansAvg", calculator.sumAndAveragePointA(dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationScans))));
		
		
		int discoveredSecurityViolations = calculator.sumAllPoints(dataService.getDataPoints(dataService.executeSQL(SQLStatement.DiscoveredSecurityViolations)));
		
		int discoveredLicenseViolations = calculator.sumAllPoints(dataService.getDataPoints(dataService.executeSQL(SQLStatement.DiscoveredLicenseViolations)));

		int fixedSecurityViolations = calculator.sumAllPoints(dataService.getDataPoints(dataService.executeSQL(SQLStatement.FixedSecurityViolations)));
		
		int fixedLicenseViolations = calculator.sumAllPoints(dataService.getDataPoints(dataService.executeSQL(SQLStatement.FixedLicenseViolations)));

		
	    int discovered = discoveredSecurityViolations + discoveredLicenseViolations;

		int fixed = fixedSecurityViolations + fixedLicenseViolations;
	    
	    float reducedRisk = (((float)fixed/discovered) * 100);

		
	    int waivedSecurityViolations = calculator.sumAllPoints(dataService.getDataPoints(dataService.executeSQL(SQLStatement.WaivedLicenseViolations)));
		
		int waivedLicenseViolations = calculator.sumAllPoints(dataService.getDataPoints(dataService.executeSQL(SQLStatement.WaivedLicenseViolations)));

		
		int discoveredCriticalLicenseViolations = calculator.sumPointA(dataService.getDataPoints(dataService.executeSQL(SQLStatement.DiscoveredLicenseViolations)));
	    
		int discoveredCriticalSecurityViolations = calculator.sumPointA(dataService.getDataPoints(dataService.executeSQL(SQLStatement.DiscoveredSecurityViolations)));

		
		model.addAttribute("countDiscoveredSecurityViolations", discoveredSecurityViolations + discoveredLicenseViolations);
		   
	    model.addAttribute("countFixedSecurityViolations", fixedSecurityViolations + fixedLicenseViolations);
	   
	    model.addAttribute("countWaivedSecurityViolations", waivedSecurityViolations + waivedLicenseViolations);
	    
	    model.addAttribute("countDiscoveredCriticalSecurityViolations", discoveredCriticalSecurityViolations + discoveredCriticalLicenseViolations);
	    
	    model.addAttribute("reducedRisk", String.format("%.02f", reducedRisk));
	    
	    
	    List<DataPoint> mostCriticalApplicationsData = dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationCriticalViolations));
	    
	    model.addAttribute("applicationCriticalViolationsAvg", calculator.sumAndAveragePointA(mostCriticalApplicationsData));
	    
	    model.addAttribute("mostCriticalApplication", new SummaryDataPoint(mostCriticalApplicationsData.get(0).getLabel(), (int) (mostCriticalApplicationsData.get(0).getPointA())));
	    
	    model.addAttribute("leastCriticalApplication", new SummaryDataPoint(mostCriticalApplicationsData.get(mostCriticalApplicationsData.size()-1).getLabel(), (int) (mostCriticalApplicationsData.get(mostCriticalApplicationsData.size()-1).getPointA())));
	    	    
	    model.addAttribute("mttrCriticalAvg", String.format("%.02f", calculator.averagePointA(dataService.getDataPoints(dataService.executeSQL(SQLStatement.MTTR)))));
		
				
	    
	    // Report Application
	    
	    
		model.addAttribute("applicationsOnboardedData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationsOnboarded)));
		
		model.addAttribute("numberOfScansData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.NumberOfScans)));
		
		model.addAttribute("applicationScansData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationScans)));
		
		String latestPeriod = dataService.executeSQL(SQLStatement.LatestTimePeriodStart).get(0).getLabel();
		
		model.addAttribute("organisationsOpenViolationsData", dataService.getDataPoints(dataService.executeSQL(calculator.AddWhereClause(SQLStatement.OrganisationsOpenViolations, latestPeriod, "ORGANIZATION_NAME"))));

		model.addAttribute("mostCriticalApplicationsData", mostCriticalApplicationsData);
		
		model.addAttribute("mostScannedApplicationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.MostScannedApplications)));
		
		model.addAttribute("mttrData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.MTTR)));
		
	    
	    // Report Security
		
		
		model.addAttribute("criticalSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.CriticalSecurityViolations)));
		
		model.addAttribute("severeSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.SevereSecurityViolations)));
		
		model.addAttribute("moderateSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.ModerateSecurityViolations)));
		
		model.addAttribute("discoveredSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.DiscoveredSecurityViolations)));
		
		model.addAttribute("fixedSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.FixedSecurityViolations)));
		
		model.addAttribute("waivedSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.WaivedSecurityViolations)));
		
		model.addAttribute("openSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.OpenSecurityViolations)));
		
	    
		// Report License
		
		
		model.addAttribute("criticalLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.CriticalLicenseViolations)));
		
		model.addAttribute("severeLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.SevereLicenseViolations)));
		
		model.addAttribute("moderateLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.ModerateLicenseViolations)));
		
	    model.addAttribute("discoveredLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.DiscoveredLicenseViolations)));

	    model.addAttribute("fixedLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.FixedLicenseViolations)));
	    
	    model.addAttribute("waivedLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.WaivedLicenseViolations)));
		
		model.addAttribute("openLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.OpenLicenseViolations)));
	    
	    
		return "unsignedReport";
	}

}
