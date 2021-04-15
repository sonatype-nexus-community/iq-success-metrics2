package org.sonatype.cs.metrics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.model.Mttr;
import org.sonatype.cs.metrics.service.DataService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.sonatype.cs.metrics.util.SqlStatementPreviousPeriod;
import org.sonatype.cs.metrics.util.SummaryDataService;
import org.sonatype.cs.metrics.util.SummaryDataServicePreviousPeriod;
import org.sonatype.cs.metrics.util.UtilService;

@Controller
public class PreviousReportController {
  private static final Logger log = LoggerFactory.getLogger(PreviousReportController.class);
  
  @Autowired
  private SummaryDataService summaryDataService;

  @Autowired
  private SummaryDataServicePreviousPeriod summaryDataServicePreviousPeriod;

  @Autowired
  private UtilService utilService;

  @GetMapping({ "/previous" })
  public String applications(Model model) throws ParseException {
    log.info("In PreviousReportController");

    Map<String, Object> periodData = summaryDataService.getPeriodData();
        
    String startPeriod = (String) periodData.get("startPeriod");
    String latestTimePeriod = (String) periodData.get("latestTimePeriod");
    String pplatestTimePeriod = utilService.getPreviousPeriod();

    Map<String, Object> applicationData = summaryDataService.getApplicationData(startPeriod);
    Map<String, Object> securityViolationsTotals = summaryDataService.getSecurityViolationsTotals();
    Map<String, Object> licenseViolationsTotals = summaryDataService.getLicenseViolationsTotals();
    Map<String, Object> securityLicenseTotals = summaryDataService.getSecurityLicenseTotals();
    Map<String, Object> violationsData = summaryDataService.getViolationsData(latestTimePeriod);

    Map<String, Object> ppapplicationData = summaryDataServicePreviousPeriod.getApplicationData(startPeriod);
    Map<String, Object> ppsecurityViolationsTotals = summaryDataServicePreviousPeriod.getSecurityViolationsTotals();
    Map<String, Object> pplicenseViolationsTotals = summaryDataServicePreviousPeriod.getLicenseViolationsTotals();
    Map<String, Object> ppsecurityLicenseTotals = summaryDataServicePreviousPeriod.getSecurityLicenseTotals();
    Map<String, Object> ppviolationsData = summaryDataServicePreviousPeriod.getViolationsData(pplatestTimePeriod);

    model.mergeAttributes(periodData);
		model.mergeAttributes(applicationData);
		model.mergeAttributes(securityViolationsTotals);
		model.mergeAttributes(licenseViolationsTotals);
		model.mergeAttributes(securityLicenseTotals);
		model.mergeAttributes(violationsData);

		model.mergeAttributes(ppapplicationData);
		model.mergeAttributes(ppsecurityViolationsTotals);
		model.mergeAttributes(pplicenseViolationsTotals);
		model.mergeAttributes(ppsecurityLicenseTotals);
		model.mergeAttributes(ppviolationsData);

    return "reportPrevious";
  }
}
