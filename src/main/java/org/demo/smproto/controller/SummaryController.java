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

		String timePeriod = dataService.getTimePeriod();
		
		model.addAttribute("timePeriod", timePeriod);
		model.addAttribute("applicationsOnboardedAvg", calculator.applicationsOnboardedAverage( qryService.getApplicationsOnboarded()));
		model.addAttribute("numberOfScansAvg", calculator.sumAndAveragePointA(qryService.getNumberOfScans()));
		model.addAttribute("applicationScansAvg", calculator.sumAndAveragePointA(qryService.getApplicationScans()));
		
		// Security
		
		int discoveredSecurityViolations = calculator.sumAllPoints(qryService.getDiscoveredSecurityViolations());
		int fixedSecurityViolations = calculator.sumAllPoints(qryService.getFixedSecurityViolations());
		int waivedSecurityViolations = calculator.sumAllPoints(qryService.getWaivedSecurityViolations());
		int discoveredCriticalSecurityViolations = calculator.sumPoint(qryService.getDiscoveredSecurityViolations(), "A");
		int discoveredSevereSecurityViolations = calculator.sumPoint(qryService.getDiscoveredSecurityViolations(), "B");
		int discoveredModerateSecurityViolations = calculator.sumPoint(qryService.getDiscoveredSecurityViolations(), "C");
		int fixedCriticalSecurityViolations = calculator.sumPoint(qryService.getFixedSecurityViolations(), "A");
		int fixedSevereSecurityViolations = calculator.sumPoint(qryService.getFixedSecurityViolations(), "B");
		int fixedModerateSecurityViolations = calculator.sumPoint(qryService.getFixedSecurityViolations(), "C");
		int waivedCriticalSecurityViolations = calculator.sumPoint(qryService.getWaivedSecurityViolations(), "A");
		int waivedSevereSecurityViolations = calculator.sumPoint(qryService.getWaivedSecurityViolations(), "B");
		int waivedModerateSecurityViolations = calculator.sumPoint(qryService.getWaivedSecurityViolations(), "C");
		
		model.addAttribute("countDiscoveredSecurityViolations", discoveredSecurityViolations);
	    model.addAttribute("countFixedSecurityViolations", fixedSecurityViolations);
	    model.addAttribute("countWaivedSecurityViolations", waivedSecurityViolations);
	    model.addAttribute("countDiscoveredCriticalSecurityViolations", discoveredCriticalSecurityViolations);
	    model.addAttribute("countDiscoveredSevereSecurityViolations", discoveredSevereSecurityViolations);
	    model.addAttribute("countDiscoveredModerateSecurityViolations", discoveredModerateSecurityViolations);
	    model.addAttribute("countFixedCriticalSecurityViolations", fixedCriticalSecurityViolations);
	    model.addAttribute("countFixedSevereSecurityViolations", fixedSevereSecurityViolations);
	    model.addAttribute("countFixedModerateSecurityViolations", fixedModerateSecurityViolations);
	    model.addAttribute("countWaivedCriticalSecurityViolations", waivedCriticalSecurityViolations);
	    model.addAttribute("countWaivedSevereSecurityViolations", waivedSevereSecurityViolations);
	    model.addAttribute("countWaivedModerateSecurityViolations", waivedModerateSecurityViolations);
	    
	    // License
	    
		int discoveredLicenseViolations = calculator.sumAllPoints(qryService.getDiscoveredLicenseViolations());
	    int fixedLicenseViolations = calculator.sumAllPoints(qryService.getFixedLicenseViolations());
	    int waivedLicenseViolations = calculator.sumAllPoints(qryService.getWaivedLicenseViolations());
	    int discoveredCriticalLicenseViolations = calculator.sumPoint(qryService.getDiscoveredLicenseViolations(), "A");
	    int discoveredSevereLicenseViolations = calculator.sumPoint(qryService.getDiscoveredLicenseViolations(), "B");
	    int discoveredModerateLicenseViolations = calculator.sumPoint(qryService.getDiscoveredLicenseViolations(), "C");
	    int fixedCriticalLicenseViolations = calculator.sumPoint(qryService.getFixedLicenseViolations(), "A");
	    int fixedSevereLicenseViolations = calculator.sumPoint(qryService.getFixedLicenseViolations(), "B");
	    int fixedModerateLicenseViolations = calculator.sumPoint(qryService.getFixedLicenseViolations(), "C");
	    int waivedCriticalLicenseViolations = calculator.sumPoint(qryService.getWaivedLicenseViolations(), "A");
	    int waivedSevereLicenseViolations = calculator.sumPoint(qryService.getWaivedLicenseViolations(), "B");
	    int waivedModerateLicenseViolations = calculator.sumPoint(qryService.getWaivedLicenseViolations(), "C");

	    model.addAttribute("countDiscoveredLicenseViolations", discoveredLicenseViolations);
	    model.addAttribute("countFixedLicenseViolations", fixedLicenseViolations);
	    model.addAttribute("countWaivedLicenseViolations", waivedLicenseViolations);
	    model.addAttribute("countDiscoveredCriticalLicenseViolations", discoveredCriticalLicenseViolations);
	    model.addAttribute("countDiscoveredSevereLicenseViolations", discoveredSevereLicenseViolations);
	    model.addAttribute("countDiscoveredModerateLicenseViolations", discoveredModerateLicenseViolations);
	    model.addAttribute("countFixedCriticalLicenseViolations", fixedCriticalLicenseViolations);
	    model.addAttribute("countFixedSevereLicenseViolations", fixedSevereLicenseViolations);
	    model.addAttribute("countFixedModerateLicenseViolations", fixedModerateLicenseViolations);
	    model.addAttribute("countWaivedCriticalLicenseViolations", waivedCriticalLicenseViolations);
	    model.addAttribute("countWaivedSevereLicenseViolations", waivedSevereLicenseViolations);
	    model.addAttribute("countWaivedModerateLicenseViolations", waivedModerateLicenseViolations);
	    
	    // Fix rate
	    
	    int discovered = discoveredSecurityViolations + discoveredLicenseViolations;
		int fixed = fixedSecurityViolations + fixedLicenseViolations;
		int waived = waivedSecurityViolations + waivedLicenseViolations;

	    float reducedRisk = (((float)(fixed + waived)/discovered) * 100);
	    
	    model.addAttribute("reducedRisk", String.format("%.02f", reducedRisk));
	    
	    // Applications
	    
	    List<DataPoint> applicationViolationsData = qryService.getApplicationsOpenViolations();
	    
		model.addAttribute("applicationViolationsData", applicationViolationsData);
	    model.addAttribute("applicationCriticalViolationsAvg", calculator.sumAndAveragePointA(applicationViolationsData));
	    model.addAttribute("mostCriticalApplication", new SummaryDataPoint(applicationViolationsData.get(0).getLabel(), (int) (applicationViolationsData.get(0).getPointA())));
	    model.addAttribute("leastCriticalApplication", new SummaryDataPoint(applicationViolationsData.get(applicationViolationsData.size()-1).getLabel(), (int) (applicationViolationsData.get(applicationViolationsData.size()-1).getPointA())));
	    	    
	    
	    // MTTR
	    
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
