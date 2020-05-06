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
public class ReportSecurityViolationsController {
	
	@Autowired 
	private IDataService dataService;
	
	private static final Logger log = LoggerFactory.getLogger(ReportSecurityViolationsController.class);

	@GetMapping({"/reportSecurityViolations"})
    public String report(Model model) {
				
		List<DataPoint3> criticalSecurityViolationsData = this.getDataPointsDP3(dataService.runSQLStatementDP3(SQLStatement.CriticalSecurityViolations));
		model.addAttribute("criticalSecurityViolationsData", criticalSecurityViolationsData);
		
		List<DataPoint3> severeSecurityViolationsData = this.getDataPointsDP3(dataService.runSQLStatementDP3(SQLStatement.SevereSecurityViolations));
		model.addAttribute("severeSecurityViolationsData", severeSecurityViolationsData);
		
		List<DataPoint3> moderateSecurityViolationsData = this.getDataPointsDP3(dataService.runSQLStatementDP3(SQLStatement.ModerateSecurityViolations));
		model.addAttribute("moderateSecurityViolationsData", moderateSecurityViolationsData);
		
		List<DataPoint3> openSecurityViolationsData = this.getDataPointsDP3(dataService.runSQLStatementDP3(SQLStatement.OpenSecurityViolations));
		model.addAttribute("openSecurityViolationsData", openSecurityViolationsData);
		

        return "reportSecurityViolations";
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> noCityFound(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
    }
	
	
	private List<DataPoint1> getDataPointsDP1(List<DataPoint1> dataList){
		
		List<DataPoint1> dataPoints = new ArrayList<DataPoint1>();

		for (DataPoint1 dp : dataList) {
			log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}
	
	private List<DataPoint3> getDataPointsDP3(List<DataPoint3> dataList){
		
		List<DataPoint3> dataPoints = new ArrayList<DataPoint3>();

		for (DataPoint3 dp : dataList) {
			log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}
	
	
	
	
}


