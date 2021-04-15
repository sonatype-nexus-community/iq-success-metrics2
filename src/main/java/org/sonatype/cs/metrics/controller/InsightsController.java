package org.sonatype.cs.metrics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import org.sonatype.cs.metrics.util.UtilService;
import org.sonatype.cs.metrics.util.SummaryDataService;
import org.sonatype.cs.metrics.util.SummaryDataServicePreviousPeriod;


@Controller
public class InsightsController {
  private static final Logger log = LoggerFactory.getLogger(InsightsController.class);

  @Autowired
  private SummaryDataService summaryDataService;

  @Autowired
  private SummaryDataServicePreviousPeriod summaryDataServicePreviousPeriod;

  @Autowired
  private UtilService utilService;
  @GetMapping({ "/insights" })
    public String applications(Model model) throws ParseException {

      log.info("In InsightsController");

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

      int[] onboardedAfter = (int[]) applicationData.get("applicationsOnboardedAvg");
      int[] onboardedBefore = (int[]) ppapplicationData.get("ppapplicationsOnboardedAvg");

      int[] scanningRateAfter = (int[]) applicationData.get("numberOfScansAvg");
      int[] scanningRateBefore = (int[]) ppapplicationData.get("ppnumberOfScansAvg");

      int[] scanningCoverageAfter = (int[]) applicationData.get("numberOfApplicationsScannedAvg"); 
      int[] scanningCoverageBefore = (int[]) ppapplicationData.get("ppnumberOfApplicationsScannedAvg"); 

      int onboardingRateBefore = 7;
      int onboardingRateAfter = 24;

      DbRow discoveredSecurityTotalAfter = (DbRow) securityViolationsTotals.get("discoveredSecurityViolationsTotals");
      DbRow discoveredSecurityTotalBefore = (DbRow) ppsecurityViolationsTotals.get("ppdiscoveredSecurityViolationsTotals");
      
      int fixedSecurityTotalAfter = (int) securityViolationsTotals.get("fixedSecurityTotal");
      int fixedSecurityTotalBefore = (int) ppsecurityViolationsTotals.get("ppfixedSecurityTotal");

      DbRow fixedSecurityCriticalsTotalAfter = (DbRow) securityViolationsTotals.get("fixedSecurityViolationsTotals");
      DbRow fixedSecurityCriticalsTotalBefore = (DbRow) ppsecurityViolationsTotals.get("ppfixedSecurityViolationsTotals");

      model.addAttribute("totalOnboardedBefore", onboardedBefore[0]);
      model.addAttribute("totalOnboardedAfter", onboardedAfter[0]);

      model.addAttribute("onboardingRateBefore", onboardedBefore[1]);
      model.addAttribute("onboardingRateAfter", onboardedAfter[1]);

      model.addAttribute("scanningCoverageBefore", scanningCoverageBefore[1]);
      model.addAttribute("scanningCoverageAfter", scanningCoverageAfter[1]);

      model.addAttribute("scanningRateBefore", scanningRateBefore[0]);
      model.addAttribute("scanningRateAfter", scanningRateAfter[0]);

      model.addAttribute("avgScansBefore", scanningRateBefore[1]);
      model.addAttribute("avgScansAfter", scanningRateAfter[1]);

      model.addAttribute("discoveryRateCriticalsBefore", discoveredSecurityTotalBefore.getPointA());
      model.addAttribute("discoveryRateCriticalsAfter", discoveredSecurityTotalAfter.getPointA());

      model.addAttribute("fixingRateBefore", fixedSecurityTotalBefore);
      model.addAttribute("fixingRateAfter", fixedSecurityTotalAfter);

      model.addAttribute("fixingRateCriticalsBefore", fixedSecurityCriticalsTotalBefore.getPointA());
      model.addAttribute("fixingRateCriticalsAfter", fixedSecurityCriticalsTotalAfter.getPointA());

      model.addAttribute("backlogReductionRateBefore", onboardingRateBefore);
      model.addAttribute("backlogReductionRateAfter", onboardingRateAfter);

      model.addAttribute("backlogReductionCriticalsRateBefore", onboardingRateBefore);
      model.addAttribute("backlogReductionCriticalsRateAfter", onboardingRateAfter);

      model.addAttribute("riskRatioBefore", onboardingRateBefore);
      model.addAttribute("riskRatioAfter", onboardingRateAfter);

      model.addAttribute("mttrCriticalsBefore", onboardingRateBefore);
      model.addAttribute("mttrCriticalsAfter", onboardingRateAfter);

      return "reportInsights";
    }
}
