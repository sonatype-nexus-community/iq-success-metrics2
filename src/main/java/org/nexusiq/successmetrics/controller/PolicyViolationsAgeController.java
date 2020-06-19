package org.nexusiq.successmetrics.controller;

import java.io.File;
import java.util.List;

import org.nexusiq.successmetrics.model.PolicyViolation;
import org.nexusiq.successmetrics.service.SQLService;
import org.nexusiq.successmetrics.service.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PolicyViolationsAgeController {
	
	private static final Logger log = LoggerFactory.getLogger(PolicyViolationsAgeController.class);

	@Autowired 
	private SQLService sqlService;
	
	@Value("${csvfile.policyviolations}")
	private String csvPolicyViolationsFileName;
	
	@GetMapping({"/policyViolationsAge"})
    public String policyviolationsAge(Model model) {
		 
		log.info("In PolicyViolationsAgeController");
		

		File f = new File(csvPolicyViolationsFileName);
		
		if(f.exists() && !f.isDirectory()) { 
			
			List<PolicyViolation> age7Data =  sqlService.executeSQLPolicyViolation(SQLStatement.PolicyViolationsAge7);
			List<PolicyViolation> age30Data = sqlService.executeSQLPolicyViolation(SQLStatement.PolicyViolationsAge30);
			List<PolicyViolation> age60Data =  sqlService.executeSQLPolicyViolation(SQLStatement.PolicyViolationsAge60);
			List<PolicyViolation> age90Data =  sqlService.executeSQLPolicyViolation(SQLStatement.PolicyViolationsAge90);

			int age7Count = age7Data.size();
			int age30Count = age30Data.size();
			int age60Count = age60Data.size();
			int age90Count = age90Data.size();

			if (age7Count > 0) {
				model.addAttribute("age7Data", age7Data);
				model.addAttribute("age7Number", age7Count);
	            model.addAttribute("age7", true);
			}
			else {
	            model.addAttribute("age7", false);
			}
			
			if (age30Count > 0) {
				model.addAttribute("age30Data", age30Data);
				model.addAttribute("age30Number", age30Count);
	            model.addAttribute("age30", true);
			}
			else {
	            model.addAttribute("age30", false);
			}
			
			if (age60Count > 0) {
				model.addAttribute("age60Data", age60Data);
				model.addAttribute("age60Number", age60Count);
	            model.addAttribute("age60", true);
			}
			else {
	            model.addAttribute("age60", false);
			}
			
			if (age90Count > 0) {
				model.addAttribute("age90Data", age90Data);
				model.addAttribute("age90Number", age90Count);
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

		return "policyViolationsAge";
		
	}

}
