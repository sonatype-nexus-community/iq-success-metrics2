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
public class SummaryController {
	
	@Autowired 
	private ModelService modelService;

	private static final Logger log = LoggerFactory.getLogger(SummaryController.class);
	
	@GetMapping({"/summary"})
	public String summary(Model model) throws ParseException {
		
		log.info("In SummaryController");

		Map <String, Object> map = modelService.setSummaryModel();
		
		model.mergeAttributes(map);
		
	    return "summary";
	}
	
}
