package org.demo.smproto.controller;

import java.util.List;

import org.demo.smproto.model.Metric;
import org.demo.smproto.service.IMetricsRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataController {
	
	private static final Logger log = LoggerFactory.getLogger(DataController.class);

	@Autowired
	private IMetricsRepositoryService metricsService;
	
	@GetMapping({"/data"})
	public String data(Model model) {
		

		List<Metric> metrics = metricsService.findByOrderByTimePeriodStartAsc();
		
        if (metrics.isEmpty()) {
        	log.info("DataController: No metrics data");
            model.addAttribute("message", "No metrics.");
            model.addAttribute("status", false);
        } 
        else {
        	log.info("DataController: Got data...count=" + metrics.size());
			model.addAttribute("metrics", metrics);	
	        model.addAttribute("status", true);
        }
        
		return "data";
	}
}
