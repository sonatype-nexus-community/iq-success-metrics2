package org.nexusiq.successmetrics.controller;

import java.util.List;

import org.nexusiq.successmetrics.model.Metric;
import org.nexusiq.successmetrics.service.IMetricsService;
import org.nexusiq.successmetrics.service.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataController {
	
	private static final Logger log = LoggerFactory.getLogger(DataController.class);

	@Autowired 
	private MetricsService metricsService;
	
	@GetMapping({"/data"})
	public String data(Model model) {
	
		List<Metric> metrics = metricsService.getAllMetrics();
				
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