package org.demo.smproto.controller;

import java.util.ArrayList;
import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.service.IDataService;
import org.demo.smproto.service.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportLicenseViolationsController {
	
	@Autowired 
	private IDataService dataService;
	
	private static final Logger log = LoggerFactory.getLogger(ReportLicenseViolationsController.class);

	
	@GetMapping({"/reportLicenseViolations"})
    public String report(Model model) {
				
		// Report License
		
		
		model.addAttribute("criticalLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.CriticalLicenseViolations)));
				
		model.addAttribute("severeLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.SevereLicenseViolations)));
				
		model.addAttribute("moderateLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.ModerateLicenseViolations)));
				
		model.addAttribute("discoveredLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.DiscoveredLicenseViolations)));

		model.addAttribute("fixedLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.FixedLicenseViolations)));
			    
		model.addAttribute("waivedLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.WaivedLicenseViolations)));
				
		model.addAttribute("openLicenseViolationsData", dataService.getDataPoints(dataService.executeSQL(SQLStatement.OpenLicenseViolations)));
		
        return "reportLicenseViolations";
    }
	
}
