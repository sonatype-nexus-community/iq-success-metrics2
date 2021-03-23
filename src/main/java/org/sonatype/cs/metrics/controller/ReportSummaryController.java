package org.sonatype.cs.metrics.controller;

import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.util.SummaryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportSummaryController {
	
	private static final Logger log = LoggerFactory.getLogger(ReportSummaryController.class);

    @Autowired
    private SummaryDataService summaryDataService;

    @GetMapping({ "/reportsummary" })
    public String applications(Model model) throws ParseException {

        log.info("In  ReportSummaryController");
        
        
        Map<String, Object> periodData = summaryDataService.getPeriodData();
        
        String startPeriod = (String) periodData.get("startPeriod");
        String latestTimePeriod = (String) periodData.get("latestTimePeriod");
        //String pplatestPeriod = (String) periodData.get("pplatestPeriod");

        Map<String, Object> applicationData = summaryDataService.getApplicationData(startPeriod);
        Map<String, Object> securityViolationsTotals = summaryDataService.getSecurityViolationsTotals();
        Map<String, Object> licenseViolationsTotals = summaryDataService.getLicenseViolationsTotals();
        Map<String, Object> securityLicenseTotals = summaryDataService.getSecurityLicenseTotals();
        Map<String, Object> violationsData = summaryDataService.getViolationsData(latestTimePeriod);
        
        model.mergeAttributes(periodData);
		model.mergeAttributes(applicationData);
		model.mergeAttributes(securityViolationsTotals);
		model.mergeAttributes(licenseViolationsTotals);
		model.mergeAttributes(securityLicenseTotals);
		model.mergeAttributes(violationsData);

		return "reportSummary";
        
    }
}
