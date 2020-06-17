package org.demo.smproto.controller;

import java.io.File;
import java.util.List;

import org.demo.smproto.model.ApplicationEvaluation;
import org.demo.smproto.service.FileNameService;
import org.demo.smproto.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationEvaluationsController {
	
	private static final Logger log = LoggerFactory.getLogger(ApplicationEvaluationsController.class);

	@Autowired 
	private QueryService qryService;
	
	@Autowired
	private FileNameService osName;
	
	@GetMapping({"/applicationEvaluations"})
	public String ApplicationEvaluations(Model model) {
		 
		log.info("In ApplicationEvaluationsController");
		
		String csvFileName = osName.getCSVFile("applicationevaluations");

		File f = new File(csvFileName);
		
		if(f.exists() && !f.isDirectory()) { 
			
			List<ApplicationEvaluation> age7Data = qryService.getApplicationEvaluationsAge7();
			List<ApplicationEvaluation> age30Data = qryService.getApplicationEvaluationsAge30();
			List<ApplicationEvaluation> age60Data = qryService.getApplicationEvaluationsAge60();
			List<ApplicationEvaluation> age90Data = qryService.getApplicationEvaluationsAge90();

			int age7Count = age7Data.size();
			int age30Count = age30Data.size();
			int age60Count = age60Data.size();
			int age90Count = age90Data.size();

			if (age7Count > 0) {
				model.addAttribute("applicationEvaluationsAge7Data", age7Data);
				model.addAttribute("applicationEvaluationsAge7Number", age7Count);
	            model.addAttribute("age7", true);
			}
			else {
	            model.addAttribute("age7", false);
			}
			
			if (age30Count > 0) {
				model.addAttribute("applicationEvaluationsAge30Data", age30Data);
				model.addAttribute("applicationEvaluationsAge30Number", age30Count);
	            model.addAttribute("age30", true);
			}
			else {
	            model.addAttribute("age30", false);
			}
			
			if (age60Count > 0) {
				model.addAttribute("applicationEvaluationsAge60Data", age60Data);
				model.addAttribute("applicationEvaluationsAge60Number", age60Count);
	            model.addAttribute("age60", true);
			}
			else {
	            model.addAttribute("age60", false);
			}
			
			if (age90Count > 0) {
				model.addAttribute("applicationEvaluationsAge90Data", age90Data);
				model.addAttribute("applicationEvaluationsAge90Number", age90Count);
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

		return "applicationEvaluations";
		
	}
}
