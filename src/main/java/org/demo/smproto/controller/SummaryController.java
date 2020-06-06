package org.demo.smproto.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.MTTRPoint;
import org.demo.smproto.model.SummaryDataPoint;
import org.demo.smproto.service.CalculatorService;
import org.demo.smproto.service.IDataService;
import org.demo.smproto.service.QueryService;
import org.demo.smproto.service.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SummaryController {
	
	@Autowired 
	private IDataService dataService;
	
	@Autowired 
	private CalculatorService calculator;
	
	@Autowired 
	private QueryService qryService;

	private static final Logger log = LoggerFactory.getLogger(SummaryController.class);
	
	@GetMapping({"/summary"})
	public String summary(Model model) throws ParseException {
		
		log.info("In SummaryController");

		
String timePeriod = qryService.getTimePeriod();
		
		model.addAttribute("timePeriod", timePeriod);

		model.addAttribute("applicationsOnboardedAvg", calculator.applicationsOnboardedAverage( qryService.getApplicationsOnboarded()));
		
		model.addAttribute("numberOfScansAvg", calculator.sumAndAveragePointA(qryService.getNumberOfScans()));
		
		model.addAttribute("applicationScansAvg", calculator.sumAndAveragePointA(qryService.getApplicationScans()));
		
		
		int discoveredSecurityViolations = calculator.sumAllPoints(qryService.getDiscoveredSecurityViolations());
		
		int discoveredLicenseViolations = calculator.sumAllPoints(qryService.getDiscoveredLicenseViolations());

		int fixedSecurityViolations = calculator.sumAllPoints(qryService.getFixedSecurityViolations());
		
		int fixedLicenseViolations = calculator.sumAllPoints(qryService.getFixedLicenseViolations());

		
	    int discovered = discoveredSecurityViolations + discoveredLicenseViolations;

		int fixed = fixedSecurityViolations + fixedLicenseViolations;
	    
	    float reducedRisk = (((float)fixed/discovered) * 100);

		
	    int waivedSecurityViolations = calculator.sumAllPoints(qryService.getWaivedSecurityViolations());
		
		int waivedLicenseViolations = calculator.sumAllPoints(qryService.getWaivedLicenseViolations());

		
		int discoveredCriticalLicenseViolations = calculator.sumPointA(qryService.getDiscoveredLicenseViolations());
	    
		int discoveredCriticalSecurityViolations = calculator.sumPointA(qryService.getDiscoveredSecurityViolations());

		
		model.addAttribute("countDiscoveredSecurityViolations", discoveredSecurityViolations + discoveredLicenseViolations);
		   
	    model.addAttribute("countFixedSecurityViolations", fixedSecurityViolations + fixedLicenseViolations);
	   
	    model.addAttribute("countWaivedSecurityViolations", waivedSecurityViolations + waivedLicenseViolations);
	    
	    model.addAttribute("countDiscoveredCriticalSecurityViolations", discoveredCriticalSecurityViolations + discoveredCriticalLicenseViolations);
	    
	    model.addAttribute("reducedRisk", String.format("%.02f", reducedRisk));
	    
	    
	    List<DataPoint> mostCriticalApplicationsData = qryService.getApplicationCriticalViolations();
	    
	    model.addAttribute("applicationCriticalViolationsAvg", calculator.sumAndAveragePointA(mostCriticalApplicationsData));
	    
	    model.addAttribute("mostCriticalApplication", new SummaryDataPoint(mostCriticalApplicationsData.get(0).getLabel(), (int) (mostCriticalApplicationsData.get(0).getPointA())));
	    
	    model.addAttribute("leastCriticalApplication", new SummaryDataPoint(mostCriticalApplicationsData.get(mostCriticalApplicationsData.size()-1).getLabel(), (int) (mostCriticalApplicationsData.get(mostCriticalApplicationsData.size()-1).getPointA())));
	    	    
	    List<Float> pointA = new ArrayList<>();	
	    List<Float> pointB = new ArrayList<>();	
	    List<Float> pointC = new ArrayList<>();	
	    
	    for (MTTRPoint dp : dataService.getMTTRPoints(dataService.executeSQL3(SQLStatement.MTTR))) {
	    	pointA.add(dp.getPointA());
	    	pointB.add(dp.getPointB());
	    	pointC.add(dp.getPointC());
		}
	    
	    model.addAttribute("mttrCriticalAvg", String.format("%.02f", calculator.averagePoint(pointA)));
	    model.addAttribute("mttrSevereAvg", String.format("%.02f", calculator.averagePoint(pointB)));
	    model.addAttribute("mttrModerateAvg", String.format("%.02f", calculator.averagePoint(pointC)));		
	    
	    return "summary";
	}
	
}
