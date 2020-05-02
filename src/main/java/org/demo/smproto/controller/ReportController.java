package org.demo.smproto.controller;

import java.util.ArrayList;
import java.util.List;

import org.demo.smproto.SmprotoApplication;
import org.demo.smproto.model.DataPoint1;
import org.demo.smproto.model.DataPoint3;
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
	
	private static final Logger log = LoggerFactory.getLogger(SmprotoApplication.class);

	@GetMapping({"/report"})
    public String report(Model model) {
				
		List<DataPoint1> applicationsOnboardedData = this.getDataPointsDP1(dataService.runSQLStatementDP1(SQLStatement.ApplicationsOnboarded));
		model.addAttribute("applicationsOnboardedData", applicationsOnboardedData);
		
		List<DataPoint1> applicationsScannedData = this.getDataPointsDP1(dataService.runSQLStatementDP1(SQLStatement.ApplicationsScanned));
		model.addAttribute("applicationsScannedData", applicationsScannedData);
		
		List<DataPoint1> applicationScansData = this.getDataPointsDP1(dataService.runSQLStatementDP1(SQLStatement.ApplicationScans));
		model.addAttribute("applicationScansData", applicationScansData);
		
		List<DataPoint3> criticalViolationsData = this.getDataPointsDP3(dataService.runSQLStatementDP3(SQLStatement.CriticalViolations));
		model.addAttribute("criticalViolationsData", criticalViolationsData);
		
		List<DataPoint1> mostCriticalApplicationsData = this.getDataPointsDP1(dataService.runSQLStatementDP1(SQLStatement.MostCriticalApplications));
		model.addAttribute("mostCriticalApplicationsData", mostCriticalApplicationsData);

        return "report";
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> noCityFound(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
    }
	
	
	private List<DataPoint1> getDataPointsDP1(List<DataPoint1> dataList){
		
		List<DataPoint1> dataPoints = new ArrayList<DataPoint1>();

		for (DataPoint1 dp : dataList) {
			//log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}
	
	private List<DataPoint3> getDataPointsDP3(List<DataPoint3> dataList){
		
		List<DataPoint3> dataPoints = new ArrayList<DataPoint3>();

		for (DataPoint3 dp : dataList) {
			//log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}
	
	
}


