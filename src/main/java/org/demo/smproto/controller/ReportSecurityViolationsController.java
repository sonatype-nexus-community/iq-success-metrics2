package org.demo.smproto.controller;

import org.demo.smproto.service.QueryService;
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
	
	
	private static final Logger log = LoggerFactory.getLogger(ReportSecurityViolationsController.class);
	
	@Autowired 
	private QueryService qryService;

	@GetMapping({"/reportSecurityViolations"})
    public String report(Model model) {
				
		// Report Security
		
		log.info("in ReportSecurityViolationsController");
		
		model.addAttribute("criticalSecurityViolationsData", qryService.getCriticalSecurityViolations());
        
	    model.addAttribute("severeSecurityViolationsData", qryService.getSevereSecurityViolations());
	        
	    model.addAttribute("moderateSecurityViolationsData", qryService.getModerateSecurityViolations());
	        
	    model.addAttribute("discoveredSecurityViolationsData", qryService.getDiscoveredSecurityViolations());

	    model.addAttribute("fixedSecurityViolationsData", qryService.getFixedSecurityViolations());
	          
	    model.addAttribute("waivedSecurityViolationsData", qryService.getWaivedSecurityViolations());
	        
	    model.addAttribute("openSecurityViolationsData", qryService.getOpenSecurityViolations());
		

        return "reportSecurityViolations";
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> noCityFound(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
    }
}


