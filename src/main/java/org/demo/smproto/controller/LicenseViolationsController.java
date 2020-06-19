package org.demo.smproto.controller;

import java.util.Map;

import org.demo.smproto.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LicenseViolationsController {
	
	private static final Logger log = LoggerFactory.getLogger(LicenseViolationsController.class);

	@Autowired 
	private ModelService modelService;
	
	@GetMapping({"/licenseViolations"})
    public String licenseViolations(Model model) {
						
		log.info("In LicenseViolationsController");

		Map <String, Object> map = modelService.setLicenseReportModel();
		
		model.mergeAttributes(map);

        return "licenseViolations";
    }
}
