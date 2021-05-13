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
	    
	    int appsInFirstPeriod = (int) p1metrics.get("startPeriodCount");
	    int p1numberOfPeriods = (int) p1metrics.get("numberOfPeriods");
	    int p2numberOfPeriods = (int) p2metrics.get("numberOfPeriods");

	    int onboardedAfter = (int) p2metrics.get("applicationsOnboarded");
	    int onboardedBefore = (int) p1metrics.get("applicationsOnboarded");
	    
//	    int onboardedAfterAvg = (int) p2metrics.get("applicationsOnboardedAvg");
//	    int onboardedBeforeAvg = (int) p1metrics.get("applicationsOnboardedAvg");

	    float scanningRateAfter = (int) p2metrics.get("numberOfScans")/p2numberOfPeriods;
	    float scanningRateBefore = (int) p1metrics.get("numberOfScans")/p1numberOfPeriods;
	    
	    float scanningRateAfterAvg = scanningRateAfter/onboardedAfter;
	    float scanningRateBeforeAvg = scanningRateBefore/onboardedBefore;

	    float scanningCoverageAfter = this.calculatePct((int)p2metrics.get("numberOfApplicationsScannedAvg"), onboardedAfter);
	    float scanningCoverageBefore = this.calculatePct((int)p1metrics.get("numberOfApplicationsScannedAvg"), onboardedBefore);

	    
	    DbRow discoveredSecurityTotalAfter = (DbRow) p2metrics.get("discoveredSecurityViolationsTotal");
	    DbRow discoveredSecurityTotalBefore = (DbRow) p1metrics.get("discoveredSecurityViolationsTotal");
	    
	    float discoveryRateCriticalsBefore = (discoveredSecurityTotalBefore.getPointA()/p1numberOfPeriods)/onboardedBefore;
	    float discoveryRateCriticalsAfter = (discoveredSecurityTotalAfter.getPointA()/p2numberOfPeriods)/onboardedAfter;
	    
	    
	    
	    DbRow fixedSecurityCriticalsTotalAfter = (DbRow) p2metrics.get("fixedSecurityViolationsTotal");
	    DbRow fixedSecurityCriticalsTotalBefore = (DbRow) p1metrics.get("fixedSecurityViolationsTotal");

	    float fixedRateCriticalsBefore = (fixedSecurityCriticalsTotalBefore.getPointA()/p1numberOfPeriods)/onboardedBefore;
	    float fixedRateCriticalsAfter = (fixedSecurityCriticalsTotalAfter.getPointA()/p2numberOfPeriods)/onboardedAfter;
	    
	    float backlogReductionCriticalsRateBefore = (float) p1metrics.get("backlogReductionRateCritical");
	    float backlogReductionCriticalsRateAfter = (float) p2metrics.get("backlogReductionRateCritical");

	    
	    
	    model.put("totalOnboardedBefore", onboardedBefore);
	    model.put("totalOnboardedAfter", onboardedAfter);
	    model.put("totalOnboarded", this.calculateChangeRate(onboardedBefore, onboardedAfter));
	    model.put("totalOnboardedIncrease", this.calculateMultipleIncrease(onboardedBefore, onboardedAfter));

	    model.put("onboardingRateBefore", (onboardedBefore - appsInFirstPeriod)/p1numberOfPeriods);
	    model.put("onboardingRateAfter", (onboardedAfter - onboardedBefore)/p2numberOfPeriods);
	    model.put("onboardingRate", this.calculateChangeRate(onboardedBefore, onboardedAfter));
	    model.put("onboardingRateIncrease", this.calculateMultipleIncrease(onboardedBefore, onboardedAfter));

	    model.put("scanningCoverageBefore", this.formatFloat(scanningCoverageBefore));
	    model.put("scanningCoverageAfter", this.formatFloat(scanningCoverageAfter));
	    model.put("scanningCoverage", this.formatFloat(scanningCoverageAfter - scanningCoverageBefore));
	    model.put("scanningCoverageIncrease", this.calculateMultipleIncrease(scanningCoverageBefore, scanningCoverageAfter));

	    model.put("scanningRateBefore", this.formatFloat(scanningRateBefore));
	    model.put("scanningRateAfter", this.formatFloat(scanningRateAfter));
	    model.put("scanningRate", this.calculateChangeRate(scanningRateBefore, scanningRateAfter));
	    model.put("scanningRateIncrease", this.calculateMultipleIncrease(scanningRateBefore, scanningRateAfter));

	    model.put("avgScansBefore", this.formatFloat(scanningRateBeforeAvg));
	    model.put("avgScansAfter", this.formatFloat(scanningRateAfterAvg));
	    model.put("avgScans", this.calculateChangeRate(scanningRateBefore, scanningRateAfter));
	    model.put("avgScansIncrease", this.calculateMultipleIncrease(scanningRateBefore, scanningRateAfter));

	    model.put("discoveryRateCriticalsBefore", this.formatFloat(discoveryRateCriticalsBefore));
	    model.put("discoveryRateCriticalsAfter", this.formatFloat(discoveryRateCriticalsAfter));
	    model.put("discoveryRateCriticals", this.calculateChangeRate(discoveryRateCriticalsBefore, discoveryRateCriticalsAfter));
	    model.put("discoveryRateCriticalsIncrease", this.calculateMultipleIncrease(discoveryRateCriticalsBefore, discoveryRateCriticalsAfter));

	    model.put("fixingRateCriticalsBefore", this.formatFloat(fixedRateCriticalsBefore));
	    model.put("fixingRateCriticalsAfter", this.formatFloat(fixedRateCriticalsAfter));
	    model.put("fixingRateCriticals", this.calculateChangeRate(fixedRateCriticalsBefore, fixedRateCriticalsAfter));
	    model.put("fixingRateCriticalsIncrease", this.calculateMultipleIncrease(fixedRateCriticalsBefore, fixedRateCriticalsAfter));

	    model.put("backlogReductionCriticalsRateBefore", this.formatFloatPct(backlogReductionCriticalsRateBefore));
	    model.put("backlogReductionCriticalsRateAfter", this.formatFloatPct(backlogReductionCriticalsRateAfter));
	    model.put("backlogReductionCriticalsRateDelta", this.formatFloatPct(backlogReductionCriticalsRateAfter - backlogReductionCriticalsRateBefore));
	    model.put("backlogReductionCriticalsRate", this.calculateChangeRate(backlogReductionCriticalsRateBefore, backlogReductionCriticalsRateAfter));
	    model.put("backlogReductionCriticalsRateIncrease", this.calculateMultipleIncrease(backlogReductionCriticalsRateBefore, backlogReductionCriticalsRateAfter));

	    int discoveredCriticalBefore = (int) p1metrics.get("discoveredCritical");
	    int discoveredCriticalAfter = (int) p2metrics.get("discoveredCritical");
	    float riskRatioBefore = discoveredCriticalBefore/onboardedBefore;
	    float riskRatioAfter = discoveredCriticalAfter/onboardedAfter;

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

  private String formatFloat(float f) {
	  return String.format("%.2f", f);
  }
  
  private String formatFloatPct(float f) {
	  return String.format("%.2f", f) + "%";
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
  
  private float calculatePct(float a, float b){
	float result = 0;

    if (a > 0 && b > 0){
    	result = ((a / b) * 100);
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
