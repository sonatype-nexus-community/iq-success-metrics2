package org.sonatype.cs.metrics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.SuccessMetricsApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Value("${sm.database}")
	private String smdatabase;
	
	@GetMapping({"/", "/home"})
	public String home(Model model) {
        
		log.info("In HomeController");
		
		model.addAttribute("smdatabase", smdatabase);
		model.addAttribute("successmetricsreport", SuccessMetricsApplication.successMetricsFileLoaded);
		model.addAttribute("policyViolationsreport", SuccessMetricsApplication.policyViolationsDataLoaded);
		model.addAttribute(("applicationEvaluationsreport"), SuccessMetricsApplication.applicationEvaluationsFileLoaded);
		model.addAttribute(("componentsqreport"), SuccessMetricsApplication.componentsQuarantineLoaded);
		model.addAttribute(("componentWaiversReport"), SuccessMetricsApplication.componentWaiversLoaded);

		return "home";
	}
}
