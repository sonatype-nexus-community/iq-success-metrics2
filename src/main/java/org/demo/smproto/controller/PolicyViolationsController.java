package org.demo.smproto.controller;

import java.io.File;
import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.PolicyViolation;
import org.demo.smproto.service.OSNameService;
import org.demo.smproto.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PolicyViolationsController {
	
	private static final Logger log = LoggerFactory.getLogger(PolicyViolationsController.class);

	@Autowired 
	private QueryService qryService;
	
	@Autowired
	private OSNameService osName;
	
	@GetMapping({"/policyviolations"})
    public String policyviolations(Model model) {
		 
		log.info("In PolicyViolationsController");
		
		String csvFileName = osName.getCSVPolicyViolationsFilePath();

		File f = new File(csvFileName);
		
		if(f.exists() && !f.isDirectory()) { 
		
			List<PolicyViolation> ninety = qryService.getPolicyViolationsAge90();
			
			//if (each is > 0)
	
			model.addAttribute("policyViolationsAge90Data", qryService.getPolicyViolationsAge90());
			model.addAttribute("policyViolationsAge60Data", qryService.getPolicyViolationsAge60());
			model.addAttribute("policyViolationsAge30Data", qryService.getPolicyViolationsAge30());
			
			model.addAttribute("policyViolationsAge90Number", qryService.getPolicyViolationsAge90().size());
			model.addAttribute("policyViolationsAge60Number", qryService.getPolicyViolationsAge60().size());
			model.addAttribute("policyViolationsAge30Number", qryService.getPolicyViolationsAge30().size());
			
			log.info("PolicyViolationsController: Got data.");
	        model.addAttribute("status", true);
		}
		else {
			log.info("PolicyViolationsController: No metrics data");
            model.addAttribute("message", "[No data]");
            model.addAttribute("status", false);
		}

		return "policyViolations";
		
	}

}
