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
	    
	    float scanningCoverageAfter = this.calculatePct((int)p2metrics.get("numberOfApplicationsScannedAvg"), onboardedAfter);
	    float scanningCoverageBefore = this.calculatePct((int)p1metrics.get("numberOfApplicationsScannedAvg"), onboardedBefore);
	    
	    float scanningRateAfter = (int) p2metrics.get("numberOfScans")/p2numberOfPeriods;
	    float scanningRateBefore = (int) p1metrics.get("numberOfScans")/p1numberOfPeriods;
	    
	    float scanningRateAfterAvg = scanningRateAfter/onboardedAfter;
	    float scanningRateBeforeAvg = scanningRateBefore/onboardedBefore;

	    DbRow discoveredSecurityTotalAfter = (DbRow) p2metrics.get("discoveredSecurityViolationsTotal");
	    DbRow discoveredSecurityTotalBefore = (DbRow) p1metrics.get("discoveredSecurityViolationsTotal");
	    
	    float discoveryRateCriticalsBefore = (float) (discoveredSecurityTotalBefore.getPointA()/p1numberOfPeriods)/onboardedBefore;
	    float discoveryRateCriticalsAfter = (float) (discoveredSecurityTotalAfter.getPointA()/p2numberOfPeriods)/onboardedAfter;
	    
	    DbRow fixedSecurityCriticalsTotalAfter = (DbRow) p2metrics.get("fixedSecurityViolationsTotal");
	    DbRow fixedSecurityCriticalsTotalBefore = (DbRow) p1metrics.get("fixedSecurityViolationsTotal");

	    float fixedRateCriticalsBefore = (float) (fixedSecurityCriticalsTotalBefore.getPointA()/p1numberOfPeriods)/onboardedBefore;
	    float fixedRateCriticalsAfter = (float) (fixedSecurityCriticalsTotalAfter.getPointA()/p2numberOfPeriods)/onboardedAfter;
	    
	    float backlogReductionCriticalsRateBefore = (float) p1metrics.get("backlogReductionRateCritical");
	    float backlogReductionCriticalsRateAfter = (float) p2metrics.get("backlogReductionRateCritical");

	    
	    model.put("totalOnboardedBefore", onboardedBefore);
	    model.put("totalOnboardedAfter", onboardedAfter);
	    model.put("totalOnboarded", this.calculateChangePctg(onboardedBefore, onboardedAfter));
	    model.put("totalOnboardedIncrease", this.calculateChangeMultiple(onboardedBefore, onboardedAfter));

	    float onboardingRateBefore = (float) (onboardedBefore - appsInFirstPeriod)/p1numberOfPeriods;
	    float onboardingRateAfter = (float) (onboardedAfter - onboardedBefore)/p2numberOfPeriods;
	    
	    model.put("onboardingRateBefore", this.formatFloat(onboardingRateBefore));
	    model.put("onboardingRateAfter", this.formatFloat(onboardingRateAfter));
	    model.put("onboardingRate", this.calculateChangePctg(onboardingRateBefore, onboardingRateAfter));
	    model.put("onboardingRateIncrease", this.calculateChangeMultiple(onboardingRateBefore, onboardingRateAfter));

	    model.put("scanningCoverageBefore", this.formatFloat(scanningCoverageBefore));
	    model.put("scanningCoverageAfter", this.formatFloat(scanningCoverageAfter));
	    model.put("scanningCoverage", this.formatFloat(scanningCoverageAfter - scanningCoverageBefore));
	    model.put("scanningCoverageIncrease", this.calculateChangeMultiple(scanningCoverageBefore, scanningCoverageAfter));

	    model.put("scanningRateBefore", this.formatFloat(scanningRateBefore));
	    model.put("scanningRateAfter", this.formatFloat(scanningRateAfter));
	    model.put("scanningRate", this.calculateChangePctg(scanningRateBefore, scanningRateAfter));
	    model.put("scanningRateIncrease", this.calculateChangeMultiple(scanningRateBefore, scanningRateAfter));

	    model.put("avgScansBefore", this.formatFloat(scanningRateBeforeAvg));
	    model.put("avgScansAfter", this.formatFloat(scanningRateAfterAvg));
	    model.put("avgScans", this.calculateChangePctg(scanningRateBefore, scanningRateAfter));
	    model.put("avgScansIncrease", this.calculateChangeMultiple(scanningRateBefore, scanningRateAfter));

	    model.put("discoveryRateCriticalsBefore", this.formatFloat(discoveryRateCriticalsBefore));
	    model.put("discoveryRateCriticalsAfter", this.formatFloat(discoveryRateCriticalsAfter));
	    model.put("discoveryRateCriticals", this.calculateChangePctg(discoveryRateCriticalsBefore, discoveryRateCriticalsAfter));
	    model.put("discoveryRateCriticalsIncrease", this.calculateChangeMultiple(discoveryRateCriticalsBefore, discoveryRateCriticalsAfter));

	    model.put("fixingRateCriticalsBefore", this.formatFloat(fixedRateCriticalsBefore));
	    model.put("fixingRateCriticalsAfter", this.formatFloat(fixedRateCriticalsAfter));
	    model.put("fixingRateCriticals", this.calculateChangePctg(fixedRateCriticalsBefore, fixedRateCriticalsAfter));
	    model.put("fixingRateCriticalsIncrease", this.calculateChangeMultiple(fixedRateCriticalsBefore, fixedRateCriticalsAfter));

	    model.put("backlogReductionCriticalsRateBefore", this.formatFloat(backlogReductionCriticalsRateBefore));
	    model.put("backlogReductionCriticalsRateAfter", this.formatFloat(backlogReductionCriticalsRateAfter));
	    model.put("backlogReductionCriticalsRate", this.calculateChangePctg(backlogReductionCriticalsRateBefore, backlogReductionCriticalsRateAfter));
	    model.put("backlogReductionCriticalsRateIncrease", this.calculateChangeMultiple(backlogReductionCriticalsRateBefore, backlogReductionCriticalsRateAfter));

//	    int discoveredCriticalBefore = (int) p1metrics.get("discoveredCritical");
//	    int discoveredCriticalAfter = (int) p2metrics.get("discoveredCritical");
//	    float riskRatioBefore = discoveredCriticalBefore/onboardedBefore;
//	    float riskRatioAfter = discoveredCriticalAfter/onboardedAfter;
	    
	    float riskRatioBefore = (int) p1metrics.get("riskRatioAtEndPeriod");
	    float riskRatioAfter = (int) p2metrics.get("riskRatioAtEndPeriod");
	    

	    model.put("riskRatioBefore", riskRatioBefore);
	    model.put("riskRatioAfter", riskRatioAfter);
	    model.put("riskRatio", this.calculateChangePctg(riskRatioBefore, riskRatioAfter));
	    model.put("riskRatioIncrease", this.calculateChangeMultiple(riskRatioBefore, riskRatioAfter));

	    String[] mttrCriticalsBefore = (String[]) p1metrics.get("mttrAvg");
	    String[] mttrCriticalsAfter = (String[]) p2metrics.get("mttrAvg");
	    
	    model.put("mttrCriticalsBefore", Integer.parseInt(mttrCriticalsBefore[0]));
	    model.put("mttrCriticalsAfter", Integer.parseInt(mttrCriticalsAfter[0]));
	    model.put("mttrCriticals", this.calculateChangePctg(Integer.parseInt(mttrCriticalsBefore[0]), Integer.parseInt(mttrCriticalsAfter[0])));
	    model.put("mttrCriticalsIncrease", this.calculateChangeMultiple(Integer.parseInt(mttrCriticalsBefore[0]), Integer.parseInt(mttrCriticalsAfter[0])));

	    return model;
	  }

	private String formatFloat(float f) {
		return String.format("%.2f", f);
	}
  
	private String division (float a, float b) {
	  float result = 0;

	  if (a > 0 && b > 0){
		  result = a / b;
	  }
	    
	  return String.format("%.2f", result);
	}
	
	private String calculateChangePctg(float before, float after){
	    float result = 0;

	    if (after > 0 && before > 0){
	    	result = (((after - before) / before) * 100);
	    }

	    return String.format("%.2f", result);
	}

	private String calculateChangeMultiple(float before, float after){
	    float result = 0;
	
	    if (after > 0 && before > 0){
	    	result = after / before;
	    }
	    
	    return String.format("%.2f", result);
	}
  
	private float calculatePct(float after, float before){
		float result = 0;
	
	    if (after > 0 && before > 0){
	    	result = ((after / before) * 100);
	    }
	    
	    return result;
	}

//  private String calculateChangePct(float b, float a){
//	  String result;
//
//    if (a > 0 && b > 0){
//      double c = (((a - b) / b) * 100);
//      result = String.format("%.2f", c);
//    }
//    else {
//      result = "0";
//    }
//    
//    return result;
//  }

//  private String calculateChangeMultiple(float b, float a){
//    String result;
//
//    if (a > 0 && b > 0){
//      double c = a / b;
//      result = String.format("%.2f", c); 
//    }
//    else {
//      result = "0";
//    }
//    
//    return result;
//  }


  
}
