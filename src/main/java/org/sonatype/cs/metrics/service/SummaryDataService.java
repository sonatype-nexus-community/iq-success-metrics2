package org.sonatype.cs.metrics.service;

import org.sonatype.cs.metrics.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.model.Mttr;
import org.sonatype.cs.metrics.service.DataService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.sonatype.cs.metrics.util.UtilService;

@Service
public class SummaryDataService {

  @Autowired
  private DataService dataService;

  @Autowired
  private UtilService timePeriodService;

  public Context setSummaryData() throws ParseException {

    Context model = new Context();

    String latestTimePeriod = timePeriodService.latestPeriod();
    String timePeriod = timePeriodService.getTimePeriod();
      
    model.setVariable("timePeriod", timePeriod);
          
    List<DbRow> applicationsOnboarded = dataService.runSql(SqlStatement.ApplicationsOnboarded);
    List<DbRow> numberOfScans = dataService.runSql(SqlStatement.NumberOfScans);
    List<DbRow> numberOfApplicationsScanned = dataService.runSql(SqlStatement.NumberOfApplicationsScanned);
    List<Mttr> mttr = dataService.runSqlMttr(SqlStatement.MTTR);

    model.setVariable("applicationsOnboarded", applicationsOnboarded);
    model.setVariable("numberOfScans", numberOfScans);
    model.setVariable("numberOfApplicationsScanned", numberOfApplicationsScanned);
    model.setVariable("mttr", mttr);

    model.setVariable("applicationsOnboardedAvg", sumAndAverageApplicationsOnboarded(applicationsOnboarded));
		model.setVariable("numberOfScansAvg", sumAndAveragePointA(numberOfScans));
		model.setVariable("numberOfApplicationsScannedAvg", sumAndAveragePointA(numberOfApplicationsScanned));

        DbRow discoveredSecurityViolationsTotals = dataService.runSql(SqlStatement.DiscoveredSecurityViolationsTotals).get(0);
        DbRow openSecurityViolationsTotals = dataService.runSql(SqlStatement.OpenSecurityViolationsTotals).get(0);
        DbRow fixedSecurityViolationsTotals = dataService.runSql(SqlStatement.FixedSecurityViolationsTotals).get(0);
        DbRow waivedSecurityViolationsTotals = dataService.runSql(SqlStatement.WaivedSecurityViolationsTotals).get(0);

        model.setVariable("discoveredSecurityViolationsTotals", discoveredSecurityViolationsTotals);
        model.setVariable("openSecurityViolationsTotals", openSecurityViolationsTotals);
        model.setVariable("fixedSecurityViolationsTotals", fixedSecurityViolationsTotals);
        model.setVariable("waivedSecurityViolationsTotals", waivedSecurityViolationsTotals);

        DbRow discoveredLicenseViolationsTotals = dataService.runSql(SqlStatement.DiscoveredLicenseViolationsTotals).get(0);
        DbRow openLicenseViolationsTotals = dataService.runSql(SqlStatement.OpenLicenseViolationsTotals).get(0);
        DbRow fixedLicenseViolationsTotals = dataService.runSql(SqlStatement.FixedLicenseViolationsTotals).get(0);
        DbRow waivedLicenseViolationsTotals = dataService.runSql(SqlStatement.WaivedLicenseViolationsTotals).get(0);

        model.setVariable("discoveredLicenseViolationsTotals", discoveredLicenseViolationsTotals);
        model.setVariable("openLicenseViolationsTotals", openLicenseViolationsTotals);
        model.setVariable("fixedLicenseViolationsTotals", fixedLicenseViolationsTotals);
        model.setVariable("waivedLicenseViolationsTotals", waivedLicenseViolationsTotals);

        int discoveredSecurityTotal = discoveredSecurityViolationsTotals.getPointA()+discoveredSecurityViolationsTotals.getPointB()+discoveredSecurityViolationsTotals.getPointC();
        int fixedSecurityTotal = fixedSecurityViolationsTotals.getPointA()+fixedSecurityViolationsTotals.getPointB()+fixedSecurityViolationsTotals.getPointC();
        int waivedSecurityTotal = waivedSecurityViolationsTotals.getPointA()+waivedSecurityViolationsTotals.getPointB()+waivedSecurityViolationsTotals.getPointC();

        int discoveredLicenseTotal = discoveredLicenseViolationsTotals.getPointA()+discoveredLicenseViolationsTotals.getPointB()+discoveredLicenseViolationsTotals.getPointC();
        int fixedLicenseTotal = fixedLicenseViolationsTotals.getPointA()+fixedLicenseViolationsTotals.getPointB()+fixedLicenseViolationsTotals.getPointC();
        int waivedLicenseTotal = waivedLicenseViolationsTotals.getPointA()+waivedLicenseViolationsTotals.getPointB()+waivedLicenseViolationsTotals.getPointC();

        int fixedWaived = fixedSecurityTotal+waivedSecurityTotal+fixedLicenseTotal+waivedLicenseTotal;
        int discovered = discoveredSecurityTotal+discoveredLicenseTotal;

        float fixRate = (((float)(fixedWaived)/discovered) * 100);

        model.setVariable("fixRate", String.format("%.0f", fixRate));

        model.setVariable("mttrAvg", this.MttrAvg());

        String applicationOpenViolations = SqlStatement.ApplicationsOpenViolations + " where time_period_start = '" + latestTimePeriod + "' group by application_name" + " order by 2 desc, 3 desc";
        List<DbRow> aov = dataService.runSql(applicationOpenViolations);

        model.setVariable("mostCriticalApplicationCount", aov.get(0).getPointA());
        model.setVariable("leastCriticalApplicationCount", aov.get(aov.size()-1).getPointA());

        model.setVariable("openCriticalViolationsAvg", this.sumAndAveragePointA(aov)[1]);

        List<DbRow> securityViolations = dataService.runSql(SqlStatement.SecurityViolations);
        List<DbRow> discoveredSecurityViolations = dataService.runSql(SqlStatement.DiscoveredSecurityViolations);
        List<DbRow> openSecurityViolations = dataService.runSql(SqlStatement.OpenSecurityViolations);
        List<DbRow> fixedSecurityViolations = dataService.runSql(SqlStatement.FixedSecurityViolations);
        List<DbRow> waivedSecurityViolations = dataService.runSql(SqlStatement.WaivedSecurityViolations);

        model.setVariable("securityViolations", securityViolations);
        model.setVariable("discoveredSecurityViolations", discoveredSecurityViolations);
        model.setVariable("openSecurityViolations", openSecurityViolations);
		model.setVariable("fixedSecurityViolations", fixedSecurityViolations);
        model.setVariable("waivedSecurityViolations", waivedSecurityViolations);
        
        List<DbRow> licenseViolations = dataService.runSql(SqlStatement.LicenseViolations);
        List<DbRow> discoveredLicenseViolations = dataService.runSql(SqlStatement.DiscoveredLicenseViolations);
        List<DbRow> openLicenseViolations = dataService.runSql(SqlStatement.OpenLicenseViolations);
        List<DbRow> fixedLicenseViolations = dataService.runSql(SqlStatement.FixedLicenseViolations);
        List<DbRow> waivedLicenseViolations = dataService.runSql(SqlStatement.WaivedLicenseViolations);

        model.setVariable("LicenseViolations", licenseViolations);
        model.setVariable("discoveredLicenseViolations", discoveredLicenseViolations);
        model.setVariable("openLicenseViolations", openLicenseViolations);
		model.setVariable("fixedLicenseViolations", fixedLicenseViolations);
		model.setVariable("waivedLicenseViolations", waivedLicenseViolations);

        return model;
    }

    private int[] sumAndAverageApplicationsOnboarded(List<DbRow> dataList) {
		
		int countLabels = dataList.size();
		int numberOfApplications = (int) dataList.get(dataList.size() - 1).getPointA();
        int dataAverage = numberOfApplications/countLabels;
        
        int total = numberOfApplications;
        int avg = dataAverage;

        int[] values = new int[]{total, avg};
		return values;
    }
    
    public int[] sumAndAveragePointA(List<DbRow> dataList) {
		
		int countLabels = 0;
		int sumData = 0;
		
		for (DbRow dp : dataList) {
			int count = (int) dp.getPointA();
			
			if (count > 0) {
				sumData += count;
				countLabels++;
			}
        }
        
        int total = sumData;
        int avg = sumData/countLabels;

        int[] values = new int[]{total, avg};
		return values;
    }

    private String[] MttrAvg(){
        List<Float> pointA = new ArrayList<>();	
	    List<Float> pointB = new ArrayList<>();	
	    List<Float> pointC = new ArrayList<>();	
	    
	    List<Mttr> mttrPoints = this.getMttr();
	    
	    for (Mttr dp : mttrPoints) {
	    	pointA.add(dp.getPointA());
	    	pointB.add(dp.getPointB());
	    	pointC.add(dp.getPointC());
		}
        
        String mttrCriticalAvg = String.format("%.0f", this.averagePoint(pointA));
        String mttrSevereAvg = String.format("%.0f", this.averagePoint(pointB));
        String mttrModerateAvg = String.format("%.0f", this.averagePoint(pointC));

        String[] values = new String[]{mttrCriticalAvg, mttrSevereAvg, mttrModerateAvg};
        return values;
    }
    
    private List<Mttr> getMttr() {
		
		List<Mttr> mttr = new ArrayList<Mttr>();
		
	    List<Mttr> points = dataService.runSqlMttr(SqlStatement.MTTR2);
	    
	    for (Mttr dp : points) {
	    	Mttr cp = new Mttr();
	        cp.setLabel(dp.getLabel());
	    	cp.setPointA(dp.getPointA()/86400000);
	    	cp.setPointB(dp.getPointB()/86400000);
	    	cp.setPointC(dp.getPointC()/86400000);

	    	mttr.add(cp);
	    }
	    
	    return mttr;
    }
    
    public Object averagePoint(List<Float> points) {
		int countPoints = 0;
		
		float sumData = 0;
		
		for (float dp : points) {
			
			if (dp > 0) {
				sumData += dp;
				countPoints++;
			}
		}
		
		return sumData/countPoints;
	}
  
}
