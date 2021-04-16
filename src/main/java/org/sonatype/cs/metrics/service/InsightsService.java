package org.sonatype.cs.metrics.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.util.SummaryDataService;
import org.sonatype.cs.metrics.util.SummaryDataServicePreviousPeriod;
import org.sonatype.cs.metrics.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class InsightsService {

  @Autowired
  private SummaryDataService summaryDataService;

  @Autowired
  private SummaryDataServicePreviousPeriod summaryDataServicePreviousPeriod;

  @Autowired
  private UtilService utilService;

  
  public Map<String, Object> insightsData() throws ParseException{
    
		Map<String, Object> model = new HashMap<>();

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

    model.put("totalOnboardedBefore", onboardedBefore[0]);
    model.put("totalOnboardedAfter", onboardedAfter[0]);
    model.put("totalOnboarded", this.calculateChangeRate(onboardedBefore[0], onboardedAfter[0]));
    model.put("totalOnboardedIncrease", this.calculateMultipleIncrease(onboardedBefore[0], onboardedAfter[0]));

    model.put("onboardingRateBefore", onboardedBefore[1]);
    model.put("onboardingRateAfter", onboardedAfter[1]);
    model.put("onboardingRate", this.calculateChangeRate(onboardedBefore[1], onboardedAfter[1]));
    model.put("onboardingRateIncrease", this.calculateMultipleIncrease(onboardedBefore[1], onboardedAfter[1]));

    model.put("scanningCoverageBefore", scanningCoverageBefore[1]);
    model.put("scanningCoverageAfter", scanningCoverageAfter[1]);
    model.put("scanningCoverage", this.calculateChangeRate(scanningCoverageBefore[1], scanningCoverageAfter[1]));
    model.put("scanningCoverageIncrease", this.calculateMultipleIncrease(scanningCoverageBefore[1], scanningCoverageAfter[1]));

    model.put("scanningRateBefore", scanningRateBefore[0]);
    model.put("scanningRateAfter", scanningRateAfter[0]);
    model.put("scanningRate", this.calculateChangeRate(scanningRateBefore[0], scanningRateAfter[0]));
    model.put("scanningRateIncrease", this.calculateMultipleIncrease(scanningRateBefore[0], scanningRateAfter[0]));

    model.put("avgScansBefore", scanningRateBefore[1]);
    model.put("avgScansAfter", scanningRateAfter[1]);
    model.put("avgScans", this.calculateChangeRate(scanningRateBefore[1], scanningRateAfter[1]));
    model.put("avgScansIncrease", this.calculateMultipleIncrease(scanningRateBefore[1], scanningRateAfter[1]));

    model.put("discoveryRateCriticalsBefore", discoveredSecurityTotalBefore.getPointA());
    model.put("discoveryRateCriticalsAfter", discoveredSecurityTotalAfter.getPointA());
    model.put("discoveryRateCriticals", this.calculateChangeRate(discoveredSecurityTotalBefore.getPointA(), discoveredSecurityTotalAfter.getPointA()));
    model.put("discoveryRateCriticalsIncrease", this.calculateMultipleIncrease(discoveredSecurityTotalBefore.getPointA(), discoveredSecurityTotalAfter.getPointA()));

    model.put("fixingRateBefore", fixedSecurityTotalBefore);
    model.put("fixingRateAfter", fixedSecurityTotalAfter);
    model.put("fixingRate", this.calculateChangeRate(fixedSecurityTotalBefore, fixedSecurityTotalAfter));
    model.put("fixingRateIncrease", this.calculateMultipleIncrease(fixedSecurityTotalBefore, fixedSecurityTotalAfter));

    model.put("fixingRateCriticalsBefore", fixedSecurityCriticalsTotalBefore.getPointA());
    model.put("fixingRateCriticalsAfter", fixedSecurityCriticalsTotalAfter.getPointA());
    model.put("fixingRateCriticals", this.calculateChangeRate(fixedSecurityCriticalsTotalBefore.getPointA(), fixedSecurityCriticalsTotalAfter.getPointA()));
    model.put("fixingRateCriticalsIncrease", this.calculateMultipleIncrease(fixedSecurityCriticalsTotalBefore.getPointA(), fixedSecurityCriticalsTotalAfter.getPointA()));

    int backlogReductionRateBefore = Integer.valueOf((String) ppsecurityLicenseTotals.get("ppbacklogReductionRate"));
    int backlogReductionRateAfter = Integer.valueOf((String) securityLicenseTotals.get("backlogReductionRate"));
    model.put("backlogReductionRateBefore", backlogReductionRateBefore);
    model.put("backlogReductionRateAfter", backlogReductionRateAfter);
    model.put("backlogReductionRate", this.calculateChangeRate(backlogReductionRateBefore, backlogReductionRateAfter));
    model.put("backlogReductionRateIncrease", this.calculateMultipleIncrease(backlogReductionRateBefore, backlogReductionRateAfter));

    int backlogReductionRateCriticalsBefore = Integer.valueOf((String) ppsecurityLicenseTotals.get("ppbacklogReductionRateCritical"));
    int backlogReductionRateCriticalsAfter = Integer.valueOf((String) securityLicenseTotals.get("backlogReductionRateCritical"));
    model.put("backlogReductionCriticalsRateBefore", backlogReductionRateCriticalsBefore);
    model.put("backlogReductionCriticalsRateAfter", backlogReductionRateCriticalsAfter);
    model.put("backlogReductionCriticalsRate", this.calculateChangeRate(backlogReductionRateCriticalsBefore, backlogReductionRateCriticalsAfter));
    model.put("backlogReductionCriticalsRateIncrease", this.calculateMultipleIncrease(backlogReductionRateCriticalsBefore, backlogReductionRateCriticalsAfter));

    Object riskRatioBefore = ppviolationsData.get("ppriskRatioInsightsCritical");
    Object riskRatioAfter = violationsData.get("riskRatioInsightsCritical");
    model.put("riskRatioBefore", riskRatioBefore);
    model.put("riskRatioAfter", riskRatioAfter);
    model.put("riskRatio", this.calculateChangePct(riskRatioBefore, riskRatioAfter));
    model.put("riskRatioIncrease", this.calculateChangeMultiple(riskRatioBefore, riskRatioAfter));

    String[] mttrCriticalsBefore = (String[]) ppsecurityLicenseTotals.get("ppmttrAvg");
    String[] mttrCriticalsAfter = (String[]) securityLicenseTotals.get("mttrAvg");
    model.put("mttrCriticalsBefore", Integer.parseInt(mttrCriticalsBefore[0]));
    model.put("mttrCriticalsAfter", Integer.parseInt(mttrCriticalsAfter[0]));
    model.put("mttrCriticals", this.calculateChangeRate(Integer.parseInt(mttrCriticalsBefore[0]), Integer.parseInt(mttrCriticalsAfter[0])));
    model.put("mttrCriticalsIncrease", this.calculateMultipleIncrease(Integer.parseInt(mttrCriticalsBefore[0]), Integer.parseInt(mttrCriticalsAfter[0])));

    return model;
  }

  private String calculateChangeRate(int before, int after){
    String result;

    if (after > 0 && before > 0){
      double rate = (((double) (after - before) / before) * 100);
      result = String.format("%.2f", rate);
    }
    else {
      result = "0";
    }

    return result;
  }

  private String calculateMultipleIncrease(int before, int after){
    String result;

    if (after > 0 && before > 0){
      double increase = (double) after / before;
      result = String.format("%.2f", increase); 
    }
    else {
      result = "0";
    }
    
    return result;

  }

  private String calculateChangePct(Object before, Object after){
    double b = Double.parseDouble((String) before);
    double a = Double.parseDouble((String) after);
    String result;

    if (a > 0 && b > 0){
      double c = (((a - b) / b) * 100);
      result = String.format("%.2f", c);
    }
    else {
      result = "0";
    }
    
    return result;
  }

  private String calculateChangeMultiple(Object before, Object after){
    double b = Double.parseDouble((String) before);
    double a = Double.parseDouble((String) after);
    String result;

    if (a > 0 && b > 0){
      double c = a / b;
      result = String.format("%.2f", c); 
    }
    else {
      result = "0";
    }
    
    return result;
  }


  
}
