package org.nexusiq.successmetrics.controller;

import org.nexusiq.successmetrics.service.FileNameService;
import org.nexusiq.successmetrics.service.NetworkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Value("${sm.data}")
	private String smdata;
	
	@GetMapping({"/", "/home"})
	public String home(Model model) {
		log.info("In HomeController");
		
		model.addAttribute("policyViolationsreport", FileNameService.PolicyViolationsReportExists);
		model.addAttribute("applicationEvaluationsreport", FileNameService.ApplicationEvaluationsReportExists);
		model.addAttribute("smdata", smdata);
		
		return "home";
	}
	
	
}
