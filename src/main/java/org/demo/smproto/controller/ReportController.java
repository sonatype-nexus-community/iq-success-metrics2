package org.demo.smproto.controller;

import java.util.ArrayList;
import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.service.IDataService;
import org.demo.smproto.service.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ReportController {
	
	@Autowired 
	private IDataService dataService;
	
	private static final Logger log = LoggerFactory.getLogger(ReportController.class);

	@GetMapping({"/report"})
    public String report(Model model) {
				
		List<DataPoint> applicationsOnboardedData = dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationsOnboarded));
		model.addAttribute("applicationsOnboardedData", applicationsOnboardedData);
		
		List<DataPoint> numberOfScansData = dataService.getDataPoints(dataService.executeSQL(SQLStatement.NumberOfScans));
		model.addAttribute("numberOfScansData", numberOfScansData);
		
		List<DataPoint> applicationScansData = dataService.getDataPoints(dataService.executeSQL(SQLStatement.ApplicationScans));
		model.addAttribute("applicationScansData", applicationScansData);
		
		List<DataPoint> lastTimePeriodStart = dataService.executeSQL(SQLStatement.LatestTimePeriodStart);
		String latestPeriod = lastTimePeriodStart.get(0).getLabel();
		
        List<DataPoint> organisationsOpenViolationsData = dataService.getDataPoints(dataService.executeSQL(this.AddWhereClause(SQLStatement.OrganisationsOpenViolations, latestPeriod, "ORGANIZATION_NAME")));
		model.addAttribute("organisationsOpenViolationsData", organisationsOpenViolationsData);

		List<DataPoint> mostCriticalApplicationsData = dataService.getDataPoints(dataService.executeSQL(SQLStatement.MostCriticalApplications));
		model.addAttribute("mostCriticalApplicationsData", mostCriticalApplicationsData);
		
		List<DataPoint> mostScannedApplicationsData = dataService.getDataPoints(dataService.executeSQL(SQLStatement.MostScannedApplications));
		model.addAttribute("mostScannedApplicationsData", mostScannedApplicationsData);
		
		List<DataPoint> mttrData = dataService.getDataPoints(dataService.executeSQL(SQLStatement.MTTR));
		model.addAttribute("mttrData", mttrData);
		

        return "report";
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> noCityFound(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
    }
	
	private String AddWhereClause(String sql, String time_period_start, String group_by ) {
		return sql + " where time_period_start = '" + time_period_start + "' group by " + group_by;
	}
	
}


