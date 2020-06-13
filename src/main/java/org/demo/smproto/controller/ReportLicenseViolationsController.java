package org.demo.smproto.controller;

import org.demo.smproto.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportLicenseViolationsController {
	
	private static final Logger log = LoggerFactory.getLogger(ReportLicenseViolationsController.class);

	@Autowired 
	private QueryService qryService;
	
	
	@GetMapping({"/reportLicenseViolations"})
    public String report(Model model) {
				
		// Report License
		
		log.info("In ReportLicenseViolationsController");

		model.addAttribute("criticalLicenseViolationsData", qryService.getCriticalLicenseViolations());
				
		model.addAttribute("severeLicenseViolationsData", qryService.getSevereLicenseViolations());
				
		model.addAttribute("moderateLicenseViolationsData", qryService.getModerateLicenseViolations());
				
		model.addAttribute("discoveredLicenseViolationsData", qryService.getDiscoveredLicenseViolations());

		model.addAttribute("fixedLicenseViolationsData", qryService.getFixedLicenseViolations());
			    
		model.addAttribute("waivedLicenseViolationsData", qryService.getWaivedLicenseViolations());
				
		model.addAttribute("openLicenseViolationsData", qryService.getOpenLicenseViolationsTrend());
		
	    model.addAttribute("licenseViolationsData", qryService.getLicenseViolations());

		
        return "reportLicenseViolations";
    }
	
}
