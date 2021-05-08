package org.sonatype.cs.metrics.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {
	private static final Logger log = LoggerFactory.getLogger(AnalysisService.class);
	
	@Autowired
	private MetricsService metricsService;

	public Map<String, Object> getAnalysisData(Map<String, Object> periodsData) throws ParseException{
		Map<String, Object> model = new HashMap<>();
	    
	    Map<String, Object> p1metrics = metricsService.getMetrics(SqlStatements.METRICP1TABLENAME, periodsData);
	    Map<String, Object> p2metrics = metricsService.getMetrics(SqlStatements.METRICP2TABLENAME, periodsData);
	    
	    int onboardedAfter = (int) p2metrics.get("applicationsOnboarded");
	    int onboardedBefore = (int) p1metrics.get("applicationsOnboarded");
	    
	    int onboardedAfterAvg = (int) p2metrics.get("applicationsOnboardedAvg");
	    int onboardedBeforeAvg = (int) p1metrics.get("applicationsOnboardedAvg");

	    int scanningRateAfter = (int) p2metrics.get("numberOfScans");
	    int scanningRateBefore = (int) p1metrics.get("numberOfScans");
	    
	    int scanningRateAfterAvg = (int) p2metrics.get("numberOfScansAvg");
	    int scanningRateBeforeAvg = (int) p1metrics.get("numberOfScansAvg");

	    int scanningCoverageAfter = (int) p2metrics.get("numberOfApplicationsScannedAvg"); 
	    int scanningCoverageBefore = (int) p1metrics.get("numberOfApplicationsScannedAvg"); 

	    DbRow discoveredSecurityTotalAfter = (DbRow) p2metrics.get("discoveredSecurityViolationsTotal");
	    DbRow discoveredSecurityTotalBefore = (DbRow) p1metrics.get("discoveredSecurityViolationsTotal");
	    
	    int fixedSecurityTotalAfter = (int) p2metrics.get("fixedSecurityTotal");
	    int fixedSecurityTotalBefore = (int) p1metrics.get("fixedSecurityTotal");

	    DbRow fixedSecurityCriticalsTotalAfter = (DbRow) p2metrics.get("fixedSecurityViolationsTotal");
	    DbRow fixedSecurityCriticalsTotalBefore = (DbRow) p1metrics.get("fixedSecurityViolationsTotal");

	    model.put("totalOnboardedBefore", onboardedBefore);
	    model.put("totalOnboardedAfter", onboardedAfter);
	    model.put("totalOnboarded", this.calculateChangeRate(onboardedBefore, onboardedAfter));
	    model.put("totalOnboardedIncrease", this.calculateMultipleIncrease(onboardedBefore, onboardedAfter));

	    model.put("onboardingRateBefore", onboardedBeforeAvg);
	    model.put("onboardingRateAfter", onboardedAfterAvg);
	    model.put("onboardingRate", this.calculateChangeRate(onboardedBefore, onboardedAfter));
	    model.put("onboardingRateIncrease", this.calculateMultipleIncrease(onboardedBefore, onboardedAfter));

	    model.put("scanningCoverageBefore", scanningCoverageBefore);
	    model.put("scanningCoverageAfter", scanningCoverageAfter);
	    model.put("scanningCoverage", this.calculateChangeRate(scanningCoverageBefore, scanningCoverageAfter));
	    model.put("scanningCoverageIncrease", this.calculateMultipleIncrease(scanningCoverageBefore, scanningCoverageAfter));

	    model.put("scanningRateBefore", scanningRateBefore);
	    model.put("scanningRateAfter", scanningRateAfter);
	    model.put("scanningRate", this.calculateChangeRate(scanningRateBefore, scanningRateAfter));
	    model.put("scanningRateIncrease", this.calculateMultipleIncrease(scanningRateBefore, scanningRateAfter));

	    model.put("avgScansBefore", scanningRateBeforeAvg);
	    model.put("avgScansAfter", scanningRateAfterAvg);
	    model.put("avgScans", this.calculateChangeRate(scanningRateBefore, scanningRateAfter));
	    model.put("avgScansIncrease", this.calculateMultipleIncrease(scanningRateBefore, scanningRateAfter));

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

	    float backlogReductionRateBefore = Float.parseFloat((String) p1metrics.get("backlogReductionRate"));
	    float backlogReductionRateAfter = Float.parseFloat((String) p2metrics.get("backlogReductionRate"));
	    model.put("backlogReductionRateBefore", backlogReductionRateBefore);
	    model.put("backlogReductionRateAfter", backlogReductionRateAfter);
	    model.put("backlogReductionRateDelta", String.format("%.2f", backlogReductionRateAfter - backlogReductionRateBefore));
	    model.put("backlogReductionRate", this.calculateChangeRate(backlogReductionRateBefore, backlogReductionRateAfter));
	    model.put("backlogReductionRateIncrease", this.calculateMultipleIncrease(backlogReductionRateBefore, backlogReductionRateAfter));

	    float backlogReductionRateCriticalsBefore = Float.parseFloat((String) p1metrics.get("backlogReductionRateCritical"));
	    float backlogReductionRateCriticalsAfter = Float.parseFloat((String) p2metrics.get("backlogReductionRateCritical"));
	    model.put("backlogReductionCriticalsRateBefore", backlogReductionRateCriticalsBefore);
	    model.put("backlogReductionCriticalsRateAfter", backlogReductionRateCriticalsAfter);
	    model.put("backlogReductionCriticalsRateDelta", String.format("%.2f", backlogReductionRateCriticalsAfter - backlogReductionRateCriticalsBefore));
	    model.put("backlogReductionCriticalsRate", this.calculateChangeRate(backlogReductionRateCriticalsBefore, backlogReductionRateCriticalsAfter));
	    model.put("backlogReductionCriticalsRateIncrease", this.calculateMultipleIncrease(backlogReductionRateCriticalsBefore, backlogReductionRateCriticalsAfter));

	    float riskRatioBefore = Float.parseFloat((String) p1metrics.get("riskRatioInsightsCritical"));
	    float riskRatioAfter = Float.parseFloat((String) p2metrics.get("riskRatioInsightsCritical"));
	    model.put("riskRatioBefore", riskRatioBefore);
	    model.put("riskRatioAfter", riskRatioAfter);
	    model.put("riskRatioDelta", String.format("%.2f", riskRatioAfter - riskRatioBefore));
	    model.put("riskRatio", this.calculateChangePct(riskRatioBefore, riskRatioAfter));
	    model.put("riskRatioIncrease", this.calculateChangeMultiple(riskRatioBefore, riskRatioAfter));

	    String[] mttrCriticalsBefore = (String[]) p1metrics.get("mttrAvg");
	    String[] mttrCriticalsAfter = (String[]) p2metrics.get("mttrAvg");
	    model.put("mttrCriticalsBefore", Integer.parseInt(mttrCriticalsBefore[0]));
	    model.put("mttrCriticalsAfter", Integer.parseInt(mttrCriticalsAfter[0]));
	    model.put("mttrCriticalsDelta", Integer.parseInt(mttrCriticalsBefore[0]) - Integer.parseInt(mttrCriticalsAfter[0]));
	    model.put("mttrCriticals", this.calculateChangeRate(Integer.parseInt(mttrCriticalsAfter[0]), Integer.parseInt(mttrCriticalsBefore[0])));
	    model.put("mttrCriticalsIncrease", this.calculateMultipleIncrease(Integer.parseInt(mttrCriticalsAfter[0]), Integer.parseInt(mttrCriticalsBefore[0])));

	    return model;
	  }

  
  private String calculateChangeRate(float before, float after){
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

  private String calculateMultipleIncrease(float before, float after){
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

  private String calculateChangePct(float b, float a){
//    double b = Double.parseDouble((String) before);
//    double a = Double.parseDouble((String) after);
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

  private String calculateChangeMultiple(float b, float a){
//    double b = Double.parseDouble((String) before);
//    double a = Double.parseDouble((String) after);
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
