package org.demo.smproto.controller;

import java.util.ArrayList;
import java.util.List;

import org.demo.smproto.SmprotoApplication;
import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.DataPointMulti;
import org.demo.smproto.service.IDataService;
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
				
		List<DataPoint> countOnboardedApplications = this.getDataPoints(dataService.countOnBoardedApplications());
		model.addAttribute("countOnboardedApplications", countOnboardedApplications);
		
		List<DataPoint> countScannedApplications = this.getDataPoints(dataService.countScannedApplications());
		model.addAttribute("countScannedApplications", countScannedApplications);
		
		List<DataPointMulti> countSecurityCriticals = this.getDataPointsMulti(dataService.countSecurityCriticals());
		model.addAttribute("countSecurityCriticals", countSecurityCriticals);
		
		for (DataPointMulti dp : countSecurityCriticals) {
			log.info("i got it: " + dp.toString());
		}

        return "report";
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> noCityFound(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
    }
	
	
	private List<DataPoint> getDataPoints(List<DataPoint> dataList){
		
		List<DataPoint> dataPoints = new ArrayList<DataPoint>();

		for (DataPoint dp : dataList) {
			log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}
	
	private List<DataPointMulti> getDataPointsMulti(List<DataPointMulti> dataList){
		
		List<DataPointMulti> dataPoints = new ArrayList<DataPointMulti>();

		for (DataPointMulti dp : dataList) {
			log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}
	
	
}


