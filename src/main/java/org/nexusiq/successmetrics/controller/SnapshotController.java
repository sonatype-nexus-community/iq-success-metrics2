package org.nexusiq.successmetrics.controller;

import java.util.ArrayList;
import java.util.List;

import org.nexusiq.successmetrics.model.DataPoint;
import org.nexusiq.successmetrics.model.Snapshot;
import org.nexusiq.successmetrics.service.SQLService;
import org.nexusiq.successmetrics.service.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SnapshotController {
	
	private static final Logger log = LoggerFactory.getLogger(SnapshotController.class);

	@Autowired 
	private SQLService sqlService;
	
	@GetMapping({"/snapshot"})
    public String snapshot(Model model) {
		log.info("In SnapshotController");

		List<String> timePeriods = new ArrayList<String>();

		for (DataPoint dp : sqlService.executeSQLMetrics(SQLStatement.TimePeriods)) {
			timePeriods.add(dp.getLabel());
		}
		
		List<String> applicationNames = new ArrayList<String>();

		for (DataPoint dp : sqlService.executeSQLMetrics(SQLStatement.ApplicationNames)) {
			applicationNames.add(dp.getLabel());
		}
		
	    model.addAttribute("snapshot", new Snapshot());
	    model.addAttribute("timePeriods", timePeriods);
	    model.addAttribute("applicationNames", applicationNames);

        return "snapshot";
    }
	
 	@PostMapping({"/snapshot"})
	public String snapshotReport(@ModelAttribute Snapshot snapshot) {
	    // logic to process input data
		return "snapshotReport";
	}

}
