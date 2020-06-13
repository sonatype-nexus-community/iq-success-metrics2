package org.demo.smproto.controller;

import java.io.File;
import java.util.List;

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
			
			List<PolicyViolation> age7Data = qryService.getPolicyViolationsAge7();
			List<PolicyViolation> age30Data = qryService.getPolicyViolationsAge30();
			List<PolicyViolation> age60Data = qryService.getPolicyViolationsAge60();
			List<PolicyViolation> age90Data = qryService.getPolicyViolationsAge90();

			int age7Count = age7Data.size();
			int age30Count = age30Data.size();
			int age60Count = age60Data.size();
			int age90Count = age90Data.size();

			if (age7Count > 0) {
				model.addAttribute("policyViolationsAge7Data", age7Data);
				model.addAttribute("policyViolationsAge7Number", age7Count);
	            model.addAttribute("age7", true);
			}
			else {
	            model.addAttribute("age7", false);
			}
			
			if (age30Count > 0) {
				model.addAttribute("policyViolationsAge30Data", age30Data);
				model.addAttribute("policyViolationsAge30Number", age30Count);
	            model.addAttribute("age30", true);
			}
			else {
	            model.addAttribute("age30", false);
			}
			
			if (age60Count > 0) {
				model.addAttribute("policyViolationsAge60Data", age60Data);
				model.addAttribute("policyViolationsAge60Number", age60Count);
	            model.addAttribute("age60", true);
			}
			else {
	            model.addAttribute("age60", false);
			}
			
			if (age90Count > 0) {
				model.addAttribute("policyViolationsAge90Data", age90Data);
				model.addAttribute("policyViolationsAge90Number", age90Count);
	            model.addAttribute("age90", true);
			}
			else {
	            model.addAttribute("age90", false);
			}

	        model.addAttribute("status", true);
		}
		else {
			log.info("PolicyViolationsController: No policy violation data");
            model.addAttribute("message", "[No data]");
            model.addAttribute("status", false);
		}

		return "policyViolations";
		
	}

}
