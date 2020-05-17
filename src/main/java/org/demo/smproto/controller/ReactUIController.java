package org.demo.smproto.controller;

import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.service.IDataService;
import org.demo.smproto.service.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactUIController {
	
	private static final Logger log = LoggerFactory.getLogger(ReactUIController.class);

	@Autowired 
	private IDataService dataService;
	
	
	@GetMapping({"/ui"})
	public List<DataPoint> react() {
		log.info("running ReactUIController");
		
		List<DataPoint> metrics = dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationsOnboarded));

//		for (DataPoint m : metrics) {
//			log.info("metric: " + m);
//		}
			
		return metrics;
	}

}
