package org.demo.smproto.controller;

import org.demo.smproto.service.CalculatorService;
import org.demo.smproto.service.IDataService;
import org.demo.smproto.service.QueryService;
import org.demo.smproto.service.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ReportApplicationsController {
	
	@Autowired 
	private IDataService dataService;
	
	@Autowired 
	private QueryService qryService;
	
	@Autowired 
	private CalculatorService calculator;
	
	private static final Logger log = LoggerFactory.getLogger(ReportApplicationsController.class);

	@GetMapping({"/applications"})
    public String report(Model model) {
						
		// Report Application
		
		log.info("In ReportApplicationsController");

	    
		model.addAttribute("applicationsOnboardedData", qryService.getApplicationsOnboarded());
		
		model.addAttribute("numberOfScansData", qryService.getNumberOfScans());
		
		model.addAttribute("applicationScansData", qryService.getApplicationScans());
				
		//model.addAttribute("organisationsOpenViolationsData", qryService.getOrganisationsOpenViolations());
		
		String latestPeriod = dataService.executeSQL(SQLStatement.LatestTimePeriodStart).get(0).getLabel();
		
		model.addAttribute("organisationsOpenViolationsData", dataService.getDataPoints(dataService.executeSQL(calculator.AddWhereClauseOrgOpenViolations(SQLStatement.OrganisationsOpenViolations, latestPeriod, "ORGANIZATION_NAME"))));


		model.addAttribute("mostCriticalApplicationsData", qryService.getApplicationsOpenViolations());
		
		model.addAttribute("mostScannedApplicationsData", qryService.getMostScannedApplications());
		
		model.addAttribute("mttrData", qryService.getMTTR());
		
		model.addAttribute("ApplicationsSecurityCriticalStatusData", qryService.getApplicationsSecurityCriticalStatus());
		
        return "applications";
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> noCityFound(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
    }
	
}


