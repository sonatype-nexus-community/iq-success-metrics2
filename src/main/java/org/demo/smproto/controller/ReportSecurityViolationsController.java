package org.demo.smproto.controller;

import java.util.ArrayList;
import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.service.IDataService;
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
public class ReportSecurityViolationsController {
	
	@Autowired 
	private IDataService dataService;
	
	private static final Logger log = LoggerFactory.getLogger(ReportSecurityViolationsController.class);

	@GetMapping({"/reportSecurityViolations"})
    public String report(Model model) {
				
		// Report Security
		
		
		model.addAttribute("criticalSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.CriticalSecurityViolations)));
		
		model.addAttribute("severeSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.SevereSecurityViolations)));
		
		model.addAttribute("moderateSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.ModerateSecurityViolations)));
		
		model.addAttribute("discoveredSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.DiscoveredSecurityViolations)));
		
		model.addAttribute("fixedSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.FixedSecurityViolations)));
		
		model.addAttribute("waivedSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.WaivedSecurityViolations)));
		
		model.addAttribute("openSecurityViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.OpenSecurityViolations)));
		

        return "reportSecurityViolations";
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> noCityFound(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
    }
}


