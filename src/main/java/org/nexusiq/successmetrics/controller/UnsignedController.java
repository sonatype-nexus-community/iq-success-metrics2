package org.nexusiq.successmetrics.controller;

import java.text.ParseException;
import java.util.Map;

import org.nexusiq.successmetrics.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UnsignedController {
	
	private static final Logger log = LoggerFactory.getLogger(UnsignedController.class);

	@Autowired 
	private ModelService modelService;
	
	@GetMapping({"/unsigned"})
	public String unsigned(Model model) throws ParseException {
		
		log.info("In UnsignedController");
		
		Map <String, Object> summaryMap = modelService.setSummaryModel();
		Map <String, Object> applicationsMap = modelService.setApplicationsReportModel();
		Map <String, Object> licenseMap = modelService.setLicenseReportModel();
		Map <String, Object> securityMap = modelService.setSecurityReportModel();

		model.mergeAttributes(summaryMap);
		model.mergeAttributes(applicationsMap);
		model.mergeAttributes(licenseMap);
		model.mergeAttributes(securityMap);

		return "unsigned";
	}

}
