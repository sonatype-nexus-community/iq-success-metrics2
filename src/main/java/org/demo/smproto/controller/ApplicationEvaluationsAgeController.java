package org.demo.smproto.controller;

import java.io.File;
import java.util.List;

import org.demo.smproto.model.ApplicationEvaluation;
import org.demo.smproto.service.SQLService;
import org.demo.smproto.service.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationEvaluationsAgeController {
	
	private static final Logger log = LoggerFactory.getLogger(ApplicationEvaluationsAgeController.class);
	
	@Autowired 
	private SQLService sqlService;
	
	@Value("${csvfile.applicationEvaluations}")
	private String csvApplicationEvaluationsFileName;
	
	@GetMapping({"/applicationEvaluationsAge"})
	public String applicationEvaluationsAge(Model model) {
		 
		log.info("In ApplicationEvaluationsAgeController");
		

		File f = new File(csvApplicationEvaluationsFileName);
		
		if(f.exists() && !f.isDirectory()) { 
			
			List<ApplicationEvaluation> age7Data =  sqlService.executeSQLApplicationEvaluation(SQLStatement.ApplicationEvaluationsAge7);
			List<ApplicationEvaluation> age30Data = sqlService.executeSQLApplicationEvaluation(SQLStatement.ApplicationEvaluationsAge30);
			List<ApplicationEvaluation> age60Data = sqlService.executeSQLApplicationEvaluation(SQLStatement.ApplicationEvaluationsAge60);
			List<ApplicationEvaluation> age90Data = sqlService.executeSQLApplicationEvaluation(SQLStatement.ApplicationEvaluationsAge90);

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
			log.info("ApplicationEvaluationsController: No application evaluation data");
            model.addAttribute("message", "[No data]");
            model.addAttribute("status", false);
		}

		return "applicationEvaluationsAge";
		
	}
}
