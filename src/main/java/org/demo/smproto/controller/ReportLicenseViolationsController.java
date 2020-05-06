package org.demo.smproto.controller;

import java.util.ArrayList;
import java.util.List;

import org.demo.smproto.model.DataPoint;
import org.demo.smproto.model.DataPoint3;
import org.demo.smproto.service.IDataService;
import org.demo.smproto.service.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportLicenseViolationsController {
	
	@Autowired 
	private IDataService dataService;
	
	private static final Logger log = LoggerFactory.getLogger(ReportLicenseViolationsController.class);

	
	@GetMapping({"/reportLicenseViolations"})
    public String report(Model model) {
				
		List<DataPoint> criticalLicenseViolationsData = this.getDataPoints(dataService.runSQLStatement(SQLStatement.CriticalLicenseViolations));
		model.addAttribute("criticalLicenseViolationsData", criticalLicenseViolationsData);
		
		List<DataPoint> severeLicenseViolationsData = this.getDataPoints(dataService.runSQLStatement(SQLStatement.SevereLicenseViolations));
		model.addAttribute("severeLicenseViolationsData", severeLicenseViolationsData);
		
		List<DataPoint> moderateLicenseViolationsData = this.getDataPoints(dataService.runSQLStatement(SQLStatement.ModerateLicenseViolations));
		model.addAttribute("moderateLicenseViolationsData", moderateLicenseViolationsData);
		
		List<DataPoint> openLicenseViolationsData = this.getDataPoints(dataService.runSQLStatement(SQLStatement.OpenLicenseViolations));
		model.addAttribute("openLicenseViolationsData", openLicenseViolationsData);
		
        return "reportLicenseViolations";
    }
	
	private List<DataPoint3> getDataPointsDP3(List<DataPoint3> dataList){
		
		List<DataPoint3> dataPoints = new ArrayList<DataPoint3>();

		for (DataPoint3 dp : dataList) {
			log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}
	
private List<DataPoint> getDataPoints(List<DataPoint> dataList){
		
		List<DataPoint> dataPoints = new ArrayList<DataPoint>();

		for (DataPoint dp : dataList) {
			log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		return dataPoints;
	}

}
