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

      int before = 50;
      int after = 100;

      DbRow discoveredSecurityTotalAfter = (DbRow) securityViolationsTotals.get("discoveredSecurityViolationsTotals");
      DbRow discoveredSecurityTotalBefore = (DbRow) ppsecurityViolationsTotals.get("ppdiscoveredSecurityViolationsTotals");
      
      int fixedSecurityTotalAfter = (int) securityViolationsTotals.get("fixedSecurityTotal");
      int fixedSecurityTotalBefore = (int) ppsecurityViolationsTotals.get("ppfixedSecurityTotal");

      DbRow fixedSecurityCriticalsTotalAfter = (DbRow) securityViolationsTotals.get("fixedSecurityViolationsTotals");
      DbRow fixedSecurityCriticalsTotalBefore = (DbRow) ppsecurityViolationsTotals.get("ppfixedSecurityViolationsTotals");

      model.addAttribute("totalOnboardedBefore", onboardedBefore[0]);
      model.addAttribute("totalOnboardedAfter", onboardedAfter[0]);
      model.addAttribute("totalOnboarded", this.calculateChangeRate(onboardedBefore[0], onboardedAfter[0]));
      model.addAttribute("totalOnboardedIncrease", this.calculateMultipleIncrease(onboardedBefore[0], onboardedAfter[0]));

      model.addAttribute("onboardingRateBefore", onboardedBefore[1]);
      model.addAttribute("onboardingRateAfter", onboardedAfter[1]);
      model.addAttribute("onboardingRate", this.calculateChangeRate(onboardedBefore[1], onboardedAfter[1]));
      model.addAttribute("onboardingRateIncrease", this.calculateMultipleIncrease(onboardedBefore[1], onboardedAfter[1]));

      model.addAttribute("scanningCoverageBefore", scanningCoverageBefore[1]);
      model.addAttribute("scanningCoverageAfter", scanningCoverageAfter[1]);
      model.addAttribute("scanningCoverage", this.calculateChangeRate(scanningCoverageBefore[1], scanningCoverageAfter[1]));
      model.addAttribute("scanningCoverageIncrease", this.calculateMultipleIncrease(scanningCoverageBefore[1], scanningCoverageAfter[1]));

      model.addAttribute("scanningRateBefore", scanningRateBefore[0]);
      model.addAttribute("scanningRateAfter", scanningRateAfter[0]);
      model.addAttribute("scanningRate", this.calculateChangeRate(scanningRateBefore[0], scanningRateAfter[0]));
      model.addAttribute("scanningRateIncrease", this.calculateMultipleIncrease(scanningRateBefore[0], scanningRateAfter[0]));

      model.addAttribute("avgScansBefore", scanningRateBefore[1]);
      model.addAttribute("avgScansAfter", scanningRateAfter[1]);
      model.addAttribute("avgScans", this.calculateChangeRate(scanningRateBefore[1], scanningRateAfter[1]));
      model.addAttribute("avgScansIncrease", this.calculateMultipleIncrease(scanningRateBefore[1], scanningRateAfter[1]));

      model.addAttribute("discoveryRateCriticalsBefore", discoveredSecurityTotalBefore.getPointA());
      model.addAttribute("discoveryRateCriticalsAfter", discoveredSecurityTotalAfter.getPointA());
      model.addAttribute("discoveryRateCriticals", this.calculateChangeRate(discoveredSecurityTotalBefore.getPointA(), discoveredSecurityTotalAfter.getPointA()));
      model.addAttribute("discoveryRateCriticalsIncrease", this.calculateMultipleIncrease(discoveredSecurityTotalBefore.getPointA(), discoveredSecurityTotalAfter.getPointA()));

      model.addAttribute("fixingRateBefore", fixedSecurityTotalBefore);
      model.addAttribute("fixingRateAfter", fixedSecurityTotalAfter);
      model.addAttribute("fixingRate", this.calculateChangeRate(fixedSecurityTotalBefore, fixedSecurityTotalAfter));
      model.addAttribute("fixingRateIncrease", this.calculateMultipleIncrease(fixedSecurityTotalBefore, fixedSecurityTotalAfter));

      model.addAttribute("fixingRateCriticalsBefore", fixedSecurityCriticalsTotalBefore.getPointA());
      model.addAttribute("fixingRateCriticalsAfter", fixedSecurityCriticalsTotalAfter.getPointA());
      model.addAttribute("fixingRateCriticals", this.calculateChangeRate(fixedSecurityCriticalsTotalBefore.getPointA(), fixedSecurityCriticalsTotalAfter.getPointA()));
      model.addAttribute("fixingRateCriticalsIncrease", this.calculateMultipleIncrease(fixedSecurityCriticalsTotalBefore.getPointA(), fixedSecurityCriticalsTotalAfter.getPointA()));

      int backlogReductionRateBefore = Integer.valueOf((String) ppsecurityLicenseTotals.get("ppbacklogReductionRate"));
      int backlogReductionRateAfter = Integer.valueOf((String) securityLicenseTotals.get("backlogReductionRate"));
      model.addAttribute("backlogReductionRateBefore", backlogReductionRateBefore);
      model.addAttribute("backlogReductionRateAfter", backlogReductionRateAfter);
      model.addAttribute("backlogReductionRate", this.calculateChangeRate(backlogReductionRateBefore, backlogReductionRateAfter));
      model.addAttribute("backlogReductionRateIncrease", this.calculateMultipleIncrease(backlogReductionRateBefore, backlogReductionRateAfter));

      int backlogReductionRateCriticalsBefore = Integer.valueOf((String) ppsecurityLicenseTotals.get("ppbacklogReductionRateCritical"));
      int backlogReductionRateCriticalsAfter = Integer.valueOf((String) securityLicenseTotals.get("backlogReductionRateCritical"));
      model.addAttribute("backlogReductionCriticalsRateBefore", backlogReductionRateCriticalsBefore);
      model.addAttribute("backlogReductionCriticalsRateAfter", backlogReductionRateCriticalsAfter);
      model.addAttribute("backlogReductionCriticalsRate", this.calculateChangeRate(backlogReductionRateCriticalsBefore, backlogReductionRateCriticalsAfter));
      model.addAttribute("backlogReductionCriticalsRateIncrease", this.calculateMultipleIncrease(backlogReductionRateCriticalsBefore, backlogReductionRateCriticalsAfter));

      Object riskRatioBefore = ppviolationsData.get("ppriskRatioInsightsCritical");
      Object riskRatioAfter = violationsData.get("riskRatioInsightsCritical");
      model.addAttribute("riskRatioBefore", riskRatioBefore);
      model.addAttribute("riskRatioAfter", riskRatioAfter);
      model.addAttribute("riskRatio", this.calculateChangePct(riskRatioBefore, riskRatioAfter));
      model.addAttribute("riskRatioIncrease", this.calculateChangeMultiple(riskRatioBefore, riskRatioAfter));

      String[] mttrCriticalsBefore = (String[]) ppsecurityLicenseTotals.get("ppmttrAvg");
      String[] mttrCriticalsAfter = (String[]) securityLicenseTotals.get("mttrAvg");
      model.addAttribute("mttrCriticalsBefore", Integer.parseInt(mttrCriticalsBefore[0]));
      model.addAttribute("mttrCriticalsAfter", Integer.parseInt(mttrCriticalsAfter[0]));
      model.addAttribute("mttrCriticals", this.calculateChangeRate(Integer.parseInt(mttrCriticalsBefore[0]), Integer.parseInt(mttrCriticalsAfter[0])));
      model.addAttribute("mttrCriticalsIncrease", this.calculateMultipleIncrease(Integer.parseInt(mttrCriticalsBefore[0]), Integer.parseInt(mttrCriticalsAfter[0])));

      return "reportInsights";
    }

    private String calculateChangeRate(int before, int after){
      double rate = (((double) (after - before) / before) * 100);
      return String.format("%.2f", rate);
    }

    private String calculateMultipleIncrease(int before, int after){
      double increase = (double) after / before;
      return String.format("%.2f", increase); 
    }

    private String calculateChangePct(Object before, Object after){
      double b = Double.parseDouble((String) before);
      double a = Double.parseDouble((String) after);
      
      double c = (((a - b) / b) * 100);
      return String.format("%.2f", c);
    }

    private String calculateChangeMultiple(Object before, Object after){
      double b = Double.parseDouble((String) before);
      double a = Double.parseDouble((String) after);

      double c = a / b;
      return String.format("%.2f", c); 
    }

}
