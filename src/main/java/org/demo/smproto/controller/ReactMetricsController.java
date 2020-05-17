package org.demo.smproto.controller;

import java.util.List;

import org.demo.smproto.model.Metric;
import org.demo.smproto.service.IMetricsRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactMetricsController {
	
	private static final Logger log = LoggerFactory.getLogger(ReactMetricsController.class);

	@Autowired
	private IMetricsRepositoryService metricsService;
	
	@GetMapping({"/metrics"})
	public List<Metric> metrics() {
		log.info("In ReactMetricsController");
		
		List<Metric> metrics = metricsService.findByOrderByTimePeriodStartAsc();
		
//		for (Metric m : metrics) {
//			log.info("metric: " + m);
//		}
			
		return metrics;
	}

}
